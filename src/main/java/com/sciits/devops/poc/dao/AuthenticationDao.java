
package com.sciits.devops.poc.dao;

import com.sciits.devops.poc.base.Response;
import com.sciits.devops.poc.exception.UMException;
import com.sciits.devops.poc.model.UserAuthDetails;

public interface AuthenticationDao {
	public boolean isValidAuthcode(String authCode) throws UMException;

	public Response logoutUser(String authCode) throws UMException;

	//public Long getUserDetailsId(String authCode) throws UMException;
	
	public UserAuthDetails getUserAuthDetails(String authCode) throws UMException;
	
}
