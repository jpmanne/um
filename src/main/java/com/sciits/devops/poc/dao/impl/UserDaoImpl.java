package com.sciits.devops.poc.dao.impl;

import static com.sciits.devops.poc.base.ResponseCodes.BAD_REQUEST;
import static com.sciits.devops.poc.base.ResponseCodes.OK;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sciits.devops.poc.base.UMDao;
import com.sciits.devops.poc.base.Response;
import com.sciits.devops.poc.base.ResponseCodes;
import com.sciits.devops.poc.common.Constants;
import com.sciits.devops.poc.dao.UserDao;
import com.sciits.devops.poc.email.EmailDetails;
import com.sciits.devops.poc.email.EmailManager;
import com.sciits.devops.poc.exception.UMException;
import com.sciits.devops.poc.model.BaseData;
import com.sciits.devops.poc.model.ChangePassword;
import com.sciits.devops.poc.model.SecondaryAuthDetailsGet;
import com.sciits.devops.poc.model.SecondaryAuthorizationDetails;
import com.sciits.devops.poc.model.UserAuthCodeDetails;
import com.sciits.devops.poc.model.UserDetails;
import com.sciits.devops.poc.model.UserSecondaryAuthorizationDetails;
import com.sciits.devops.poc.util.AuthCodeGenerator;
import com.sciits.devops.poc.util.CreateFolder;
import com.sciits.devops.poc.util.PasswordEncy;
import com.sciits.devops.poc.util.PasswordGenerator;
import com.sciits.devops.poc.util.ValidationResponse;
import com.sciits.devops.poc.util.ValidationUtils;
import com.sciits.devops.poc.web.model.WebUserDetails;
import com.sciits.devops.poc.web.model.WebUserLoginDetails;

import lombok.extern.log4j.Log4j;

@Repository
@Log4j
public class UserDaoImpl extends UMDao<Serializable, BaseData> implements UserDao {
	Logger log = Logger.getLogger(UserDaoImpl.class);
	PasswordEncy passwordEncryptor = PasswordEncy.getInstance();
	PasswordGenerator passwordGenerator = PasswordGenerator.getInstance();
	
	// ========================================================================

	@Override
	@Transactional
	public Response addUser(UserDetails userDetails) throws UMException {
		String logTag = "addUser(): ";
		log.info("Entering into : "+logTag);
		
		try {
			ValidationResponse utils = ValidationUtils.validateUser(userDetails);
			
			if (!utils.isSuccess()) {
				return new Response(false, utils.getErrorMessage(), BAD_REQUEST.getCode(), null);
			}
			String password = userDetails.getPassword();
			
			if(password != null){
				password = passwordEncryptor.encrypt(password);
				userDetails.setPassword(password);
			}	
			userDetails.setUserName(userDetails.getUserName().trim().toLowerCase()); 
			Long roleId = userDetails.getRoleDetails().getRoleId();
			
			if(Constants.SUPER_USER.compareTo(roleId) == 0 || Constants.STQ_USER_FED.compareTo(roleId) == 0 || Constants.GL_USER_FED.compareTo(roleId) == 0) {
				userDetails.setVZID("");
				userDetails.setCcAddress("");
			}
			saveOrUpdate(userDetails);
			//saveAddressDetails(userDetails, createdUser);
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while adding user: " + e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		return new Response(true, Constants.User.USER_SIGNUP, OK.getCode(), null);
	}

	// ========================================================================

	/*private void saveAddressDetails(BaseData userDetails, BaseData createdUser) {
		UserDetails userDetails2 = (UserDetails) userDetails;
		UserDetails savedUserDetails = (UserDetails) createdUser;
		List<AddressDetails> addressDetails = userDetails2.getAddressDetails();

		for (AddressDetails details : addressDetails) {
			details.setUserDetails(savedUserDetails);
			saveOrUpdate(details);
		}
	}*/

	// ========================================================================

	@Override
	public Response loginUser(UserDetails loginUser) throws UMException {
		String logTag = "loginUser(): ";
		log.info("Entering into : "+logTag);
		Session session=null;
		ValidationResponse utils = ValidationUtils.validateLoginUser(loginUser);
		
		if (!utils.isSuccess()) {
			return new Response(false, utils.getErrorMessage(), BAD_REQUEST.getCode(), null);
		}
		try {
			session = getSession();
			String password = passwordEncryptor.encrypt(loginUser.getPassword());
			Criteria criteria = session.createCriteria(UserDetails.class);
			criteria.add(Restrictions.eq("userName", loginUser.getUserName().trim().toLowerCase()));
			criteria.add(Restrictions.eq("password", password));
			UserDetails userDetails = (UserDetails) criteria.uniqueResult();
			if(userDetails != null) {
				if(Constants.ACTIVE.equalsIgnoreCase(userDetails.getStatus())) {
					AuthCodeGenerator authCodeGenerator = AuthCodeGenerator.getInstance();
					String authCode = authCodeGenerator.getGeneratedAuthCode();
					UserAuthCodeDetails authCodeDetails = new UserAuthCodeDetails();
					authCodeDetails.setAuthCode(authCode);
					authCodeDetails.setUserDetails(userDetails);
					authCodeDetails.setLoginTimestamp(new Date());
					authCodeDetails.setLogoutTimestamp(new Date());
					authCodeDetails.setStatus(Constants.ACTIVE);
					session.save(authCodeDetails); // Saving authcode
					WebUserLoginDetails webUserLoginDetails = userDetails.getWebUserLoginDetails();
					webUserLoginDetails.setIsDefaultPasswordChanged(userDetails.getIsDefaultPasswordChanged());
					webUserLoginDetails.setIsSecondaryAuthorizationSaved(userDetails.getIsSecondaryAuthorizationSaved());
					webUserLoginDetails.setAuthCode(authCode);
					session.flush();
					return new Response(true, Constants.User.USER_LOGIN, ResponseCodes.OK.getCode(), webUserLoginDetails);
				} else {
					return new Response(true, Constants.User.USER_ACCOUNT_INACTIVE, ResponseCodes.OK.getCode(), null);
				}
			}
			session.flush();
		} catch (Exception e) {
			String errMessage = logTag +" Exception occurred while logging in." + e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally{
			closeSession(session);
		}
		return new Response(false, Constants.User.USER_LOGIN_FAILED, ResponseCodes.BAD_REQUEST.getCode(), null);
	}

	// ========================================================================

	@SuppressWarnings("unchecked")
	@Override
	public Response getUsers(Long userDetailsId) throws UMException {
		String logTag = "getUsers(): ";
		log.info("Entering into : "+logTag);
		ArrayList<WebUserDetails> webUsers = null;
		Session session=null;
		
		try {
			session = getSession();
			Criteria criteria = session.createCriteria(UserDetails.class);
			criteria.add(Restrictions.ne("userDetailsId", userDetailsId));
			List<UserDetails> users = criteria.list();
			webUsers = new ArrayList<WebUserDetails>();
			
			for (UserDetails user : users) {
				webUsers.add(user.getWebUser());
			}
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while getting the users."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally{
			closeSession(session);
		}
		return new Response(Boolean.TRUE, Constants.User.USER_LIST, ResponseCodes.OK.getCode(), webUsers);
	}

	// ========================================================================

	@Override
	public Response getUser(Long userDetailsId) throws UMException {
		String logTag = "getUser(): ";
		log.info("Entering into : "+logTag);
		Session session=null;
		if (userDetailsId == null) {
			return new Response(false, Constants.User.USER_ID_NULL, ResponseCodes.BAD_REQUEST.getCode(), null);
		}
		UserDetails user = null;
		try {
			session = getSession();
			log.info(logTag + "getting the user details with the userDetailsId: "+userDetailsId);
			user = (UserDetails) session.get(UserDetails.class, userDetailsId);
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while getting the user details."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally{
			closeSession(session);
		}
		if (user == null) {
			return new Response(false, Constants.User.INVALID_USER_ID, ResponseCodes.BAD_REQUEST.getCode(), null);
		}
		return new Response(true, Constants.User.USER_DETAILS, ResponseCodes.OK.getCode(), user.getWebUser());
	}

	// ========================================================================

	@Override
	public Response updateUser(UserDetails updateUserDetails) throws UMException {
		String logTag = "updateUser(): ";
		log.info("Entering into : "+logTag);
		Session session=null;
		try {
			session = getSession();
			UserDetails userDetails = (UserDetails) session.get(UserDetails.class, updateUserDetails.getUserDetailsId());
			
			if (userDetails == null) {
				return new Response(true, Constants.User.INVALID_USER_ID, ResponseCodes.OK.getCode(), null);
			}
			userDetails.updateUserDetails(userDetails, updateUserDetails);
			Long roleId = userDetails.getRoleDetails().getRoleId();
			
			if(Constants.SUPER_USER.compareTo(roleId) == 0 || Constants.STQ_USER_FED.compareTo(roleId) == 0 || Constants.GL_USER_FED.compareTo(roleId) == 0) {
				userDetails.setVZID("");
				userDetails.setCcAddress("");
			}
			session.getTransaction().begin();
			session.saveOrUpdate(userDetails);
			session.getTransaction().commit();
			session.flush();
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while updating the user."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally{
			closeSession(session);
		}
		return new Response(true, Constants.User.USER_UPDATED, ResponseCodes.OK.getCode(), updateUserDetails.getWebUser());
	}

	// ========================================================================

	@Override
	public Response deleteUser(Long userDetailsId) throws UMException {
		String logTag = "deleteUser(): ";
		log.info("Entering into : "+logTag);
		Session session = null;
		if (userDetailsId == null) {
			return new Response(false, Constants.User.USER_ID_NULL, ResponseCodes.BAD_REQUEST.getCode(), null);
		}
		try {
			log.info(logTag + "Deleting the user with userDetailsId: "+userDetailsId);
			 session = getSession();
			UserDetails user = (UserDetails) session.get(UserDetails.class, userDetailsId);
			if (user == null) {
				return new Response(false, Constants.User.INVALID_USER_ID, ResponseCodes.BAD_REQUEST.getCode(), null);
			}
			user.setStatus(Constants.INACTIVE);
			session.getTransaction().begin();
			session.saveOrUpdate(user);
			session.getTransaction().commit();
			session.flush();
			
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while deleting the user."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally{
			closeSession(session);
		}
		return new Response(true, Constants.User.USER_DELETED, ResponseCodes.OK.getCode(), null);
	}

	// ========================================================================
	
	@Override
	public Response activateUser(Long userDetailsId) throws UMException {
		String logTag = "deleteUser(): ";
		log.info("Entering into : "+logTag);
		Session session=null;
		if (userDetailsId == null) {
			return new Response(false, Constants.User.USER_ID_NULL, ResponseCodes.BAD_REQUEST.getCode(), null);
		}
		try {
			log.info(logTag + "Activating the user with userDetailsId: "+userDetailsId);
			session = getSession();
			UserDetails user = (UserDetails) session.get(UserDetails.class, userDetailsId);
			if (user == null) {
				return new Response(false, Constants.User.INVALID_USER_ID, ResponseCodes.BAD_REQUEST.getCode(), null);
			}
			user.setStatus(Constants.ACTIVE);
			session.getTransaction().begin();
			session.saveOrUpdate(user);
			session.getTransaction().commit();
			session.flush();
			
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while deleting the user."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally{
			closeSession(session);
		}
		return new Response(true, Constants.User.USER_ACTIVATED, ResponseCodes.OK.getCode(), null);
	}

	// ========================================================================


	@Override
	public boolean isUserExist(String userName) throws UMException {
		String logTag = "isUserExist(): ";
		log.info("Entering into : "+logTag);
		Criteria criteria = null;
		Session session=null;
		boolean status=false;
		if (userName == null) {
			return false;
		}
		try {
			log.info(logTag + "Checking the user existance with the username: "+userName);
			
			if(userName != null) {
				userName = userName.trim().toLowerCase();
			}
			session = getSession();
			criteria = session.createCriteria(UserDetails.class);
			criteria.add(Restrictions.eq("userName", userName));
			criteria.setProjection(Projections.rowCount());
			 if((Long)criteria.uniqueResult() > 0)
				 status=true;
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while checking the user existance"+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally{
			closeSession(session);
		}
		return status;
	}

	// ========================================================================

	@Override
	public Response upload(CommonsMultipartFile userPhoto, Long userDetailsId) throws UMException {
		String logTag = "upload(): ";
		log.info("Entering into : "+logTag);
		UserDetails user = null;
		Session session=null;
		try {
			String filePath = CreateFolder.fileUpload(userPhoto, Constants.User.USER_PHOTO_UPLOAD_FOLDER);
			session = getSession();
			log.info(logTag+ "uploading the profile pic for the user: "+userDetailsId);
			user = (UserDetails) session.get(UserDetails.class, userDetailsId);
			user.setFilePath(filePath);
			session.getTransaction().begin();
			session.saveOrUpdate(user);
			session.getTransaction().commit();
			session.flush();
		} catch (Exception e) {
			String errMessage = logTag +"exception occurred while uploading the photo."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally{
			closeSession(session);
		}
		return new Response(true, Constants.User.USER_PHOTO_ADDED, ResponseCodes.OK.getCode(), user.getWebUser());
	}

	// ========================================================================
	
	@Override
	public UserDetails getUserDetails(Long userDetailsId) throws UMException {
		String logTag = "getUserDetails(): ";
		log.info("Entering into : "+logTag);
		UserDetails userDetails = null;
		Session session=null;
		
		try {
			session = getSession();
			userDetails = (UserDetails) session.get(UserDetails.class, userDetailsId);
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while getting the user details "+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally{
			closeSession(session);
		}
		return userDetails;
	}

	// ========================================================================
	
	@Override
	public Response changePassword(ChangePassword changePassword ,Long userDetailsId) throws UMException {
		
		String logTag = "changePassword(): ";
		log.info("Entering into : "+logTag);
		UserDetails userDetails = null;
		Session session=null;
		try {
			session = getSession();
			userDetails = (UserDetails) session.get(UserDetails.class, userDetailsId);
			
			if(!changePassword.getNewPassword().equals(changePassword.getConfirmPassword())){
				return new Response(false, "you entered new password and confirm passwords are  mismatch", ResponseCodes.BAD_REQUEST.getCode(), null);
			}
			
			if(changePassword.getOldPassword().equals(passwordEncryptor.decrypt(userDetails.getPassword()))){
				userDetails.setPassword(passwordEncryptor.encrypt(changePassword.getNewPassword()));
				session.getTransaction().begin();
				session.saveOrUpdate(userDetails);
				session.getTransaction().commit();
				session.flush();
			} else {
				return new Response(false, "you entered old password is mismatch", ResponseCodes.BAD_REQUEST.getCode(), null);
			}
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while getting the user details "+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally{
			closeSession(session);
		}
		return new Response(true, "password changed successfully", ResponseCodes.OK.getCode(), userDetails.getWebUser());
	}

	// ======================================================================== 
	@Override
	public Response firstTimeLoginChangePassword(ChangePassword changePassword ,Long userDetailsId) throws UMException {
		
		String logTag = "changePassword(): ";
		log.info("Entering into : "+logTag);
		UserDetails userDetails = null;
		Session session=null;
		
		try {
			session = getSession();
			if(userDetailsId == null)
			{
				return new Response(false, Constants.User.INVALID_USER_ID, ResponseCodes.BAD_REQUEST.getCode(), null);
			}
			userDetails = (UserDetails) session.get(UserDetails.class, userDetailsId);
			if(!changePassword.getNewPassword().equals(changePassword.getConfirmPassword())){
				return new Response(false, "you entered new password and confirm passwords are  mismatch", ResponseCodes.BAD_REQUEST.getCode(), null);
			}
			if(changePassword != null && userDetails != null )
			{
			if(changePassword.getOldPassword().equals(passwordEncryptor.decrypt(userDetails.getPassword()))){
				userDetails.setPassword(passwordEncryptor.encrypt(changePassword.getNewPassword()));
				userDetails.setIsDefaultPasswordChanged("1");
				session.getTransaction().begin();
				session.saveOrUpdate(userDetails);
				session.getTransaction().commit();
				session.flush();
			} else {
				return new Response(false, "you entered old password is mismatch", ResponseCodes.BAD_REQUEST.getCode(), null);
			}
			
			}
			else
			{
				return new Response(false, Constants.User.USER_DETAILS_NOT_EXIST, ResponseCodes.BAD_REQUEST.getCode(), null);	
			}
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while getting the user details "+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally{
			closeSession(session);
		}
		return new Response(true, "password changed successfully", ResponseCodes.OK.getCode(), userDetails.getWebUser());
	}

	
	// ==========================================================================
	
	@Override
	public Response resetPassword(Long userDetailsId) throws UMException {
		String logTag = "resetPassword(): ";
		log.info("Entering into : "+logTag);
		Session session = getSession();
		UserDetails userDetails = null;
		Criteria criteria = null;
		try{
			
			criteria = session.createCriteria(UserDetails.class);
			criteria.add(Restrictions.eq("userDetailsId", userDetailsId));
			userDetails = (UserDetails) criteria.uniqueResult();
			String password = passwordGenerator.getGeneratedAuthCode();
			userDetails.setPassword(passwordEncryptor.encrypt(password));
			session.getTransaction().begin();
			session.saveOrUpdate(userDetails);
			session.getTransaction().commit();
			session.flush();
			EmailDetails  emailDetails  = new EmailDetails();
			emailDetails.setSubject("Reset Password");
			String[] toRecipients = new String[1];
			toRecipients[0] = userDetails.getEmail();
			emailDetails.setToRecipients(toRecipients);
			emailDetails.setMessageContent("Hi"+" "+ userDetails.getFullName()+",\n\n"+ "Your password was reset by Admin.\n"+"Below is your new system generated password,\n"+password+"\nFor the security purpose please change your password after login.\n\n"+"Thanks\n"+"UM.");
			EmailManager emailManager = new EmailManager();
			boolean isMailSent = emailManager.sendEmailForRestPassword(emailDetails);
			if(isMailSent==true){
				return new Response(true, "password generated successfully and sent to user", ResponseCodes.OK.getCode(),null);
			}
			return new Response(true, "There is a problem to send email to the user", ResponseCodes.OK.getCode(),null);
		} catch(Exception e){
			String errMessage = logTag +"Exception occurred while resetting the user password "+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally{
			closeSession(session);
		}
	}
	
	// ========================================================================
	
	@Override
	public Response getSecondaryAuthorizationDetailsQuesitions() throws UMException {
		String logTag = "getSecondaryAuthorizationDetailsQuesions(): ";
		log.info("Entering into : "+logTag);
		Session session = getSession();
		List<SecondaryAuthorizationDetails> secondaryAuthorizationDetailsFirstQuesitions=new ArrayList<SecondaryAuthorizationDetails>();
		List<SecondaryAuthorizationDetails> secondaryAuthDetailsSecondQuesitions=new ArrayList<SecondaryAuthorizationDetails>();
		SecondaryAuthDetailsGet authDetailsCombo = new SecondaryAuthDetailsGet();
		try{
			String sqlQuery="SELECT * FROM secondary_authorization_details";
			session = getSession();
			SQLQuery query = session.createSQLQuery(sqlQuery);
			List list = query.list();
			log.error("list" + list.size());
			if(list !=null && !list.isEmpty()) {
			for (int i = 0; i < 10; i++) {
				SecondaryAuthorizationDetails authorizationDetails=new SecondaryAuthorizationDetails();
				Object[] obj = (Object[]) list.get(i);
				BigInteger id = (BigInteger) obj[0];
				authorizationDetails.setSecondaryAuthDetailsId(id.longValue());
				authorizationDetails.setQuesition((String) obj[1]);
				secondaryAuthorizationDetailsFirstQuesitions.add(authorizationDetails);
			}
			for (int i = 10; i < 20; i++) {
				SecondaryAuthorizationDetails authorizationDetails=new SecondaryAuthorizationDetails();
				Object[] obj = (Object[]) list.get(i);
				BigInteger id = (BigInteger) obj[0];
				authorizationDetails.setSecondaryAuthDetailsId(id.longValue());
				authorizationDetails.setQuesition((String) obj[1]);
				secondaryAuthDetailsSecondQuesitions.add(authorizationDetails);
			}
			if((secondaryAuthorizationDetailsFirstQuesitions !=null && !secondaryAuthorizationDetailsFirstQuesitions.isEmpty()) && (secondaryAuthDetailsSecondQuesitions!=null && !secondaryAuthDetailsSecondQuesitions.isEmpty()))
			{
				authDetailsCombo.setSecondaryAuthDetailsFirstQuesitionsGet(secondaryAuthorizationDetailsFirstQuesitions);
				authDetailsCombo.setSecondaryAuthDetailsSecondQuesitionsGet(secondaryAuthDetailsSecondQuesitions);
			}
			
		}
			else{
				return new Response(true, Constants.User.SECONDARY_AUTHORIZATIONS_DETAILS_NOT_AVAILABLE_IN_DATABASE, ResponseCodes.OK.getCode(), null);
			}
			return new Response(true, Constants.User.SECONDARY_USER_DETAILS, ResponseCodes.OK.getCode(), authDetailsCombo);
		} catch(Exception e) {
			String errMessage = logTag +"Exception occurred while retrieving Secondary authorization detials combo "+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally {
			closeSession(session);
		}
			
	}

	//=====================================================
	
	@SuppressWarnings("unchecked")
	@Override
	public Response getUserSecondaryAuthorizationDetails(Long userDetailsId) throws UMException {
		String logTag = "getUserSecondaryAuthorizationDetails(): ";
		log.info("Entering into : "+logTag);
		List<UserSecondaryAuthorizationDetails> userSecondaryAuthDetails = null;
		Session session=null;
		try {
			session = getSession();
			Criteria criteria = session.createCriteria(UserSecondaryAuthorizationDetails.class);
			criteria.add(Restrictions.eq("userDetailsId", userDetailsId));
			userSecondaryAuthDetails = criteria.list();
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while getting the users."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally {
			closeSession(session);
		}
		return new Response(Boolean.TRUE, Constants.User.USER_SECONDARY_AUTHORIZATION_DETAILS, ResponseCodes.OK.getCode(), userSecondaryAuthDetails);
	}

	
	//=======================================================
	
	@Override
	public Response saveUserSecondaryAuthorizationDetails(UserSecondaryAuthorizationDetails userSecondaryAuthorizationDetails) throws UMException {
		String logTag = "getUserSecondaryAuthorizationDetails(): ";
		log.info("Entering into : "+logTag);
		UserSecondaryAuthorizationDetails userSecondaryAuthDetails=null;
		UserDetails userDetails = null;
		Session session=null;
		Criteria criteria=null;
		try
		{
			session = getSession();
			criteria = session.createCriteria(UserDetails.class);
			criteria.add(Restrictions.eq("userDetailsId", userSecondaryAuthorizationDetails.getUserDetailsId()));
			criteria.add(Restrictions.eq("status",Constants.ACTIVE));
			userDetails = (UserDetails) criteria.uniqueResult();
			if(userDetails == null)
			return new Response(false, Constants.User.INVALID_USER_ID, ResponseCodes.BAD_REQUEST.getCode(), null);
			userSecondaryAuthorizationDetails.setLoginTimestamp(new Date());
			userSecondaryAuthorizationDetails.setStatus(Constants.ACTIVE);
			userSecondaryAuthDetails = (UserSecondaryAuthorizationDetails) saveOrUpdate(userSecondaryAuthorizationDetails);
			if(userSecondaryAuthDetails!=null)
			{
				userDetails.setIsSecondaryAuthorizationSaved(Constants.ACTIVE);
				session.getTransaction().begin();
				session.saveOrUpdate(userDetails);
				session.getTransaction().commit();
				session.flush();
			}
			
			
		} catch(Exception e) {
			log.error(logTag + "Exception occured while adding secondary authorization details ");
			throw new UMException(logTag + "Exception occurred while adding secondary authorization details" + e, e);
		}
		finally {
			closeSession(session);
		}
		return new Response(true, Constants.User.USER_SECONDARY_AUTHORIZATION_DETAILS_SAVE, OK.getCode(),
				userSecondaryAuthDetails);
	}
	
	
	//====================================================================
	
	@Override
	public Response updateUserSecondaryAuthorizationDetails(UserSecondaryAuthorizationDetails userSecondaryAuthorizationDetails) throws UMException {
		String logTag = "updateUserSecondaryAuthorizationDetails(): ";
		log.info("Entering into : "+logTag);
		Session session = null;
		Criteria criteria = null;
		UserSecondaryAuthorizationDetails userSecondaryAuthDetails = null;
		
		try {
			session = getSession();
			criteria = session.createCriteria(UserSecondaryAuthorizationDetails .class);
			criteria.add(Restrictions.eq("userDetailsId", userSecondaryAuthorizationDetails.getUserDetailsId()));
			criteria.add(Restrictions.eq("status",Constants.ACTIVE));
			userSecondaryAuthDetails = (UserSecondaryAuthorizationDetails) criteria.uniqueResult();
			
			if(userSecondaryAuthDetails == null)
				return new Response(false, Constants.User.USER_SECONDARY_AUTHORIZATIONS_DETAILS_NOT_SUMBMITED,BAD_REQUEST.getCode(),userSecondaryAuthDetails);	
			
			userSecondaryAuthorizationDetails.setUserSecondaryAuthDetailsId(userSecondaryAuthDetails.getUserSecondaryAuthDetailsId());
			userSecondaryAuthorizationDetails.setLoginTimestamp(userSecondaryAuthDetails.getLoginTimestamp());
			userSecondaryAuthorizationDetails.setStatus(userSecondaryAuthDetails.getStatus());
			userSecondaryAuthDetails = (UserSecondaryAuthorizationDetails) saveOrUpdate(userSecondaryAuthorizationDetails);
		} catch(Exception e) {
			log.error(logTag + "Exception occured while adding secondary authorization details ");
			throw new UMException(logTag + "Exception occurred while adding secondary authorization details" + e, e);
		}
		finally {
			closeSession(session);
		}
		return new Response(true, Constants.User.USER_SECONDARY_AUTHORIZATION_DETAILS_UPDATE, OK.getCode(),
				userSecondaryAuthDetails);
	}

	//====================================================================
	
	@SuppressWarnings("unchecked")
	@Override
	public String getEmailAddresses(Long roleId) throws UMException {
		String logTag = "getEmailAddresses(): ";
		log.info("Entering into : "+logTag);
		Session session = null;
		Criteria criteria = null;
		String emailAddresses = "";
		List<UserDetails> roleBasedUsers = null;
		
		try {
			if(roleId != null && roleId.longValue() > 0) {
				session = getSession();
				criteria = session.createCriteria(UserDetails .class);
				criteria.add(Restrictions.eq("roleDetails.roleId", roleId));
				criteria.add(Restrictions.eq("status",Constants.ACTIVE));
				roleBasedUsers = criteria.list();
				
				if(roleBasedUsers != null && !roleBasedUsers.isEmpty()) {
					for(UserDetails userDetails : roleBasedUsers) {
						emailAddresses = emailAddresses + userDetails.getEmail().trim() + ",";
					}
					emailAddresses = emailAddresses.substring(0, emailAddresses.lastIndexOf(","));
				}
			}
		} catch(Exception e) {
			String errMsg = "Exception occurred while getting the email addresses based on a role";
			log.error(logTag + errMsg);
			throw new UMException(logTag + errMsg + e, e);
		}
		finally {
			if(session != null) {
				closeSession(session);
			}
		}
		return emailAddresses;
	}
	
	//====================================================================
}
