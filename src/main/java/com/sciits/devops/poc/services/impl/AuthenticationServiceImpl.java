
package com.sciits.devops.poc.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sciits.devops.poc.base.Response;
import com.sciits.devops.poc.dao.AuthenticationDao;
import com.sciits.devops.poc.exception.UMException;
import com.sciits.devops.poc.model.UserAuthDetails;
import com.sciits.devops.poc.services.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	public static final Logger log=Logger.getLogger(AuthenticationServiceImpl.class);
	String logTag=null;
	@Autowired
	AuthenticationDao authenticationDao;

	/*@Override
	public boolean isValidAuthcode(String authCode) throws UMException {
		logTag="isValidAuthcode()";
		log.info("Entering into "+logTag+" with authCode :"+authCode);
		return authenticationDao.isValidAuthcode(authCode);
	}*/

	@Override
	public Response logoutUser(String authCode) throws UMException {
		logTag="logoutUser()";
		log.info("Entering into "+logTag+" with authCode :"+authCode);
		return authenticationDao.logoutUser(authCode);
	}

	/*@Override
	public Long getUserDetailsId(String authCode) throws UMException {
		logTag="getUserDetailsId()";
		log.info("Entering into "+logTag+" with authCode: "+authCode);
		return authenticationDao.getUserDetailsId(authCode);
	}*/

	@Override
	public UserAuthDetails getUserAuthDetails(String authCode) throws UMException {
		return authenticationDao.getUserAuthDetails(authCode);
	}


}
