package com.sciits.devops.poc.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sciits.devops.poc.base.UMDao;
import com.sciits.devops.poc.base.Response;
import com.sciits.devops.poc.base.ResponseCodes;
import com.sciits.devops.poc.common.Constants;
import com.sciits.devops.poc.dao.AuthenticationDao;
import com.sciits.devops.poc.exception.UMException;
import com.sciits.devops.poc.model.UserAuthCodeDetails;
import com.sciits.devops.poc.model.UserAuthDetails;
import com.sciits.devops.poc.model.UserDetails;

import lombok.extern.log4j.Log4j;

@Repository
@Log4j
public class AuthenticationDaoImpl extends UMDao<Serializable, UserAuthCodeDetails> implements AuthenticationDao {
	Logger log = Logger.getLogger(AuthenticationDaoImpl.class);
	// ========================================================================

	@Override
	public boolean isValidAuthcode(String authCode) throws UMException {
		String logTag = "isValidAuthcode(): ";
		Criteria criteria;
		boolean status=false;
		Session session = null;
		if (authCode == null) {
			return false;
		}
		try {
			session = getSession();
			criteria = session.createCriteria(UserAuthCodeDetails.class);
			criteria.add(Restrictions.eq("authCode", authCode));
			criteria.add(Restrictions.eq("status", Constants.ACTIVE));
			log.info(logTag + "authcode: "+authCode+", status: "+Constants.ACTIVE);
			criteria.setProjection(Projections.rowCount());
			if((Long) criteria.uniqueResult() > 0)
				status=true;
		} catch (Exception e) {
			String errMessage = logTag + "Exception occurred while validating the auth code" + e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally {
			closeSession(session);
		}

		return status;
	}

	// ========================================================================
	
	@Override
	public UserAuthDetails getUserAuthDetails(String authCode) throws UMException {
		String logTag = "getUserAuthDetails(): ";
		Criteria criteria = null;
		UserAuthCodeDetails  authCodeDetails = null;
		UserDetails userDetails = null;
		UserAuthDetails userAuthDetails = new UserAuthDetails();
		Session session = null;
		
		try {
			session = getSession();
			criteria = session.createCriteria(UserAuthCodeDetails.class);
			criteria.add(Restrictions.eq("authCode", authCode));
			criteria.add(Restrictions.eq("status", Constants.ACTIVE));
			authCodeDetails = (UserAuthCodeDetails) criteria.uniqueResult();
			
			if(authCodeDetails != null) {
				criteria = session.createCriteria(UserDetails.class);
				criteria.add(Restrictions.eq("userDetailsId", authCodeDetails.getUserDetails().getUserDetailsId()));
				userDetails = (UserDetails) criteria.uniqueResult();
				
				if(userDetails != null) {
					userAuthDetails.setValidAuthCode(true);
					userAuthDetails.setRoleId(userDetails.getRoleDetails().getRoleId());
					userAuthDetails.setUserDetailsId(userDetails.getUserDetailsId());
				}
			}
		} catch (Exception e) {
			String errMessage = logTag + "Exception occurred while validating the auth code" + e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally {
			closeSession(session);
		}
		return userAuthDetails;
		//return ((Long) criteria.uniqueResult()) > 0;
	}

	// ========================================================================

	@SuppressWarnings("unchecked")
	@Override
	public Response logoutUser(String authCode) throws UMException {
		String logTag = "logoutUser(): ";
		boolean isInvalid = false;
		Response response = new Response();
		log.info(logTag + "Logging out with auth code: "+authCode);
		Session session = null;
		try {
			if (isValidAuthcode(authCode)) {
				session = getSession();
				session.getTransaction().begin();
				Criteria criteria = session.createCriteria(UserAuthCodeDetails.class);
				criteria.add(Restrictions.eq("authCode", authCode));
				criteria.add(Restrictions.eq("status", Constants.ACTIVE));
				List<UserAuthCodeDetails> userAuthCodesDetails = criteria.list();
				
				if (userAuthCodesDetails != null && !userAuthCodesDetails.isEmpty()) {
					for (UserAuthCodeDetails authCodeDetails : userAuthCodesDetails) {
						authCodeDetails.setLogoutTimestamp(new Date());
						authCodeDetails.setStatus(Constants.INACTIVE);
						session.saveOrUpdate(authCodeDetails);
					}
					log.info(logTag + "User loggedout successfully");
					response.setMessage("User Logout Successful");
					response.setResponseCode(ResponseCodes.OK.getCode());
					response.setSuccess(true);
					session.getTransaction().commit();
				}
				else {
					session.getTransaction().rollback();
				}
				session.flush();
			} else {
				isInvalid = true;
			}
			if (isInvalid) {
				log.info(logTag + "Invalid auth code");
				response.setMessage("Invalid AuthCode");
				response.setResponseCode(ResponseCodes.OK.getCode());
			}
		} catch (Exception e) {
			String errMessage = logTag + "Exception occurred while logging out with the authcode : " + e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		finally {
			if(session!=null)
			closeSession(session);
		}
		return response;
	}

	// ========================================================================
	
	/*@Override
	public Long getUserDetailsId(String authCode) throws UMException {
		String logTag = "getUserDetailsId(): ";
		log.info("Entering into "+ logTag + "with authCode: "+authCode);
		UserAuthCodeDetails  authCodeDetails = null;
		
		try {
			Session session = getSession();
			Criteria criteria = session.createCriteria(UserAuthCodeDetails.class);
			criteria.add(Restrictions.eq("authCode", authCode));
			authCodeDetails = (UserAuthCodeDetails) criteria.uniqueResult();
		} catch (Exception e) {
			String errMessage = logTag + "Exception occurred while getting the user details Id." + e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		return authCodeDetails == null ? null : authCodeDetails.getUserDetailsId();
	}

	// ========================================================================
*/}
