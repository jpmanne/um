
package com.sciits.devops.poc.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sciits.devops.poc.base.Response;
import com.sciits.devops.poc.dao.UserDao;
import com.sciits.devops.poc.exception.UMException;
import com.sciits.devops.poc.model.ChangePassword;
import com.sciits.devops.poc.model.UserDetails;
import com.sciits.devops.poc.model.UserSecondaryAuthorizationDetails;
import com.sciits.devops.poc.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger log=Logger.getLogger(UserServiceImpl.class);
	String logTag=null;
	@Autowired
	UserDao userDao;

	@Override
	public boolean isUserExist(String userName) throws UMException {
		logTag="isUserExist()";
		log.info("Entering into"+logTag+" with userName: "+userName);
		return userDao.isUserExist(userName);
	}

	@Override
	public Response addUser(UserDetails user) throws UMException {
		logTag="addUser()";
		log.info("Entering into"+logTag+"with user details.");
		return userDao.addUser(user);
	}

	@Override
	public Response loginUser(UserDetails user) throws UMException {
		logTag="loginUser()";
		log.info("Entering into"+logTag+"with user details.");
		return userDao.loginUser(user);
	}

	@Override

	public Response getUsers(Long userDetailsId) throws UMException {
		return userDao.getUsers(userDetailsId);
	}

	@Override
	public Response getUser(Long userDetailsId) throws UMException {
		logTag="getUser()";
		log.info("Entering into"+logTag+" with userDetailsId: "+userDetailsId);
		return userDao.getUser(userDetailsId);
		
	}

	@Override
	public Response updateUser(UserDetails user) throws UMException {
		logTag="updateUser()";
		log.info("Entering into"+logTag+" with user details.");
		return userDao.updateUser(user);
	}

	@Override
	public Response deleteUser(Long userDetailsId) throws UMException {
		logTag="deleteUser()";
		log.info("Entering into"+logTag+" with userDetailsId: "+userDetailsId);
		return userDao.deleteUser(userDetailsId);
	}

	@Override
	public Response upload(CommonsMultipartFile userPhoto,Long userDetailsId) throws UMException {
		logTag="upload()";
		log.info("Entering into"+logTag+" with userPhoto and userDetailsId: "+userDetailsId);
		return userDao.upload(userPhoto,userDetailsId);
	}

	@Override
	public UserDetails getUserDetails(Long userDetailsId) throws UMException {
		logTag="getUserDetails()";
		log.info("Entering into"+logTag+" with userDetailsId: "+userDetailsId);
		return userDao.getUserDetails(userDetailsId);
	}

	@Override
	public Response changePassword(ChangePassword changePassword ,Long userDetailsId ) throws UMException {
		logTag="changePassword()";
		log.info("Entering into"+logTag+" with password: "+changePassword+" userDetailsId: "+userDetailsId);
		return userDao.changePassword(changePassword ,userDetailsId);
	}

	@Override
	public Response firstTimeLoginChangePassword(ChangePassword changePassword ,Long userDetailsId ) throws UMException {
		logTag="firstTimeLoginChangePassword()";
		log.info("Entering into"+logTag+" with password: "+changePassword+" userDetailsId: "+userDetailsId);
		return userDao.firstTimeLoginChangePassword(changePassword ,userDetailsId);
	}
	
	@Override
	public Response activateUser(Long userDetailsId) throws UMException {
		logTag="activateUser()";
		log.info("Entering into"+logTag+" with userDetailsId: "+userDetailsId);
		return userDao.activateUser(userDetailsId);
	}

	@Override
	public Response resetPassword(Long userDetailsId) throws UMException {
		return userDao.resetPassword(userDetailsId);
	}
	@Override
	public Response getSecondaryAuthorizationDetailsQuesions() throws UMException {
		return userDao.getSecondaryAuthorizationDetailsQuesitions();
	}
	@Override
	public Response getUserSecondaryAuthorizationDetails(Long userDetailsId) throws UMException {
		return userDao.getUserSecondaryAuthorizationDetails(userDetailsId);
	}
	@Override
	public Response saveUserSecondaryAuthorizationDetails(UserSecondaryAuthorizationDetails userSecondaryAuthorizationDetails) throws UMException {
	
		return userDao.saveUserSecondaryAuthorizationDetails(userSecondaryAuthorizationDetails);
	}
	@Override
	public Response updateUserSecondaryAuthorizationDetails(UserSecondaryAuthorizationDetails userSecondaryAuthorizationDetails) throws UMException {
		return userDao.updateUserSecondaryAuthorizationDetails(userSecondaryAuthorizationDetails);
	}

	@Override
	public String getEmailAddresses(Long roleId) throws UMException {
		return userDao.getEmailAddresses(roleId);
	}
}
