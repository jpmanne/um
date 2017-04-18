
package com.sciits.devops.poc.dao;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sciits.devops.poc.base.Response;
import com.sciits.devops.poc.exception.UMException;
import com.sciits.devops.poc.model.ChangePassword;
import com.sciits.devops.poc.model.UserDetails;
import com.sciits.devops.poc.model.UserSecondaryAuthorizationDetails;

public interface UserDao {
	public boolean isUserExist(String userName) throws UMException;

	public Response addUser(UserDetails user) throws UMException;

	public Response loginUser(UserDetails user) throws UMException;

	public Response getUsers(Long userDetailsId) throws UMException;

	public Response getUser(Long userDetailsId) throws UMException;

	public Response updateUser(UserDetails user) throws UMException;

	public Response deleteUser(Long userDetailsId) throws UMException;
	
	public Response activateUser(Long userDetailsId) throws UMException;
	
	public Response upload(CommonsMultipartFile userPhoto,Long userDetailsId) throws UMException;
	
    public UserDetails getUserDetails(Long userDetailsId) throws UMException;
    
    public Response changePassword(ChangePassword changePassword ,Long userDetailsId) throws UMException;
    
    public Response firstTimeLoginChangePassword(ChangePassword changePassword ,Long userDetailsId) throws UMException;

    public Response resetPassword(Long userDetailsId) throws UMException;
    
    public Response getSecondaryAuthorizationDetailsQuesitions() throws UMException;
    
    public Response getUserSecondaryAuthorizationDetails(Long userDetailsId) throws UMException;
    
    public Response saveUserSecondaryAuthorizationDetails(UserSecondaryAuthorizationDetails userSecondaryAuthorizationDetails) throws UMException;
    
    public Response updateUserSecondaryAuthorizationDetails(UserSecondaryAuthorizationDetails userSecondaryAuthorizationDetails) throws UMException;
    
    public String getEmailAddresses(Long roleId) throws UMException;
}
