package com.sciits.devops.poc.services;

import com.sciits.devops.poc.base.Response;
import com.sciits.devops.poc.exception.UMException;
import com.sciits.devops.poc.model.UserAuthDetails;

public interface AuthenticationService {
	//public boolean isValidAuthcode(String authCode) throws UMException;
	
    public Response logoutUser(String authCode) throws UMException;
    
    //public Long getUserDetailsId(String authCode) throws UMException;
    
    public UserAuthDetails getUserAuthDetails(String authCode) throws UMException;
}
