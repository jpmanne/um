package com.sciits.devops.poc.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sciits.devops.poc.base.Response;
import com.sciits.devops.poc.common.Constants;
import com.sciits.devops.poc.common.UrlConstants;
import com.sciits.devops.poc.exception.UMException;
import com.sciits.devops.poc.model.ChangePassword;
import com.sciits.devops.poc.model.UserAuthDetails;
import com.sciits.devops.poc.model.UserDetails;
import com.sciits.devops.poc.model.UserSecondaryAuthorizationDetails;
import com.sciits.devops.poc.services.AuthenticationService;
import com.sciits.devops.poc.services.UserService;

@Controller
@CrossOrigin
@RequestMapping(value=UrlConstants.User.API_BASE)
public class UserController extends BaseController {
	Logger log = Logger.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	@Autowired
	AuthenticationService authenticationService;
	
	//========================================================================
	
	@RequestMapping(value = UrlConstants.User.IS_USER_EXIST, method = RequestMethod.GET)
	public @ResponseBody Response isUserExist(@RequestParam String userName, @RequestParam String authCode) throws UMException {
		String logTag = "isUserExist(): ";
		log.info("Entering into : "+logTag+ "with authCode: "+authCode);
		UserAuthDetails userAuthDetails = null;
		
		try {
			userAuthDetails = authenticationService.getUserAuthDetails(authCode);
			
			if(userAuthDetails.isValidAuthCode()) {
				if(Constants.SUPER_USER.compareTo(userAuthDetails.getRoleId()) == 0) {
					Response response = new Response();
					response.setSuccess(userService.isUserExist(userName));
					return response;
				} else {
					return getInvalidAccessResponse();
				}
			} else {
				return getInvalidAuthCodeResponse();
			}
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while checking the authCode or user name existance"+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
	}

	//========================================================================
	
	@RequestMapping(value = UrlConstants.User.SIGNUP, method = RequestMethod.POST)
	public @ResponseBody Response userSignup(@RequestBody UserDetails user, @RequestParam String authCode) throws UMException {
		String logTag = "userSignup(): ";
		log.info("Entering into : "+logTag+ "with authCode: "+authCode);
		UserAuthDetails userAuthDetails = null;
		
		try {
			userAuthDetails = authenticationService.getUserAuthDetails(authCode);
			
			if(userAuthDetails.isValidAuthCode()) {
				if(Constants.SUPER_USER.compareTo(userAuthDetails.getRoleId()) == 0) {
					return userService.addUser(user);
				} else {
					return getInvalidAccessResponse();
				}
			} else {
				return getInvalidAuthCodeResponse();
			}
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while checking the authCode existance or when trying  add user to database."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
	}

	//========================================================================
	
	@RequestMapping(value = UrlConstants.User.LOGIN, method = RequestMethod.POST)
	public @ResponseBody Response userLogin(@RequestBody UserDetails user) throws UMException {
		String logTag = "userLogin(): ";
		log.info("Entering into : "+logTag);
		try {
			return userService.loginUser(user);
		} catch (Exception e) {
			String errMessage = logTag +" Exception occurred while logging in." + e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
	}

	//========================================================================
	
	@RequestMapping(value = UrlConstants.User.GET_ALL_USERS, method = RequestMethod.GET)
	public @ResponseBody Response getUsers(@RequestParam String authCode) throws UMException {
		String logTag = "getUsers(): ";
		log.info("Entering into : "+logTag+ "with authCode: "+authCode);
		UserAuthDetails userAuthDetails = null;
		
		try {
			userAuthDetails = authenticationService.getUserAuthDetails(authCode);
			
			if(userAuthDetails.isValidAuthCode()) {
				if(Constants.SUPER_USER.compareTo(userAuthDetails.getRoleId()) == 0) {
					return userService.getUsers(userAuthDetails.getUserDetailsId());
				} else {
					return getInvalidAccessResponse();
				}
			} else {
				return getInvalidAuthCodeResponse();
			}
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while checking the authCode existance or when getting all users."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
	}

	//========================================================================
	
	@RequestMapping(value = UrlConstants.User.GET_USER, method = RequestMethod.GET)
	public @ResponseBody Response getUser(@RequestParam Long userDetailsId, @RequestParam String authCode) throws UMException {
		String logTag = "getUser(): ";
		log.info("Entering into : "+logTag+ "with authCode: "+authCode+", userId: "+userDetailsId);
		UserAuthDetails userAuthDetails = null;
		
		try {
			userAuthDetails = authenticationService.getUserAuthDetails(authCode);
			
			if(userAuthDetails.isValidAuthCode()) {
				return userService.getUser(userDetailsId);
			} else {
				return getInvalidAuthCodeResponse();
			}
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while checking the authCode existance or when getting the user."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
	}
	
	//========================================================================

	@RequestMapping(value = UrlConstants.User.UPDATE_USER, method = RequestMethod.POST)
	public @ResponseBody Response updateUser(@RequestBody UserDetails user, @RequestParam String authCode) throws UMException {
		String logTag = "updateUser(): ";
		log.info("Entering into : "+logTag+ "with authCode: "+authCode);
		UserAuthDetails userAuthDetails = null;
		
		try {
			userAuthDetails = authenticationService.getUserAuthDetails(authCode);
			
			if(userAuthDetails.isValidAuthCode()) {
				return userService.updateUser(user);
			} else {
				return getInvalidAuthCodeResponse();
			}
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while checking the authCode existance or when trying to update the user."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
	}

	//========================================================================
	
	@RequestMapping(value = UrlConstants.User.DELETE_USER, method = RequestMethod.GET)
	public @ResponseBody Response deleteUser(@RequestParam Long userDetailsId, @RequestParam String authCode) throws UMException {
		String logTag = "deleteUser(): ";
		log.info("Entering into : "+logTag+ "with authCode: "+authCode+", userId: "+userDetailsId);
		UserAuthDetails userAuthDetails = null;
		
		try {
			userAuthDetails = authenticationService.getUserAuthDetails(authCode);
			
			if(userAuthDetails.isValidAuthCode()) {
				if(Constants.SUPER_USER.compareTo(userAuthDetails.getRoleId()) == 0) {
					return userService.deleteUser(userDetailsId);
				} else {
					return getInvalidAccessResponse();
				}
			} else {
				return getInvalidAuthCodeResponse();
			}
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while checking the authCode existance or when trying to delete the user."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
	}
	
	//========================================================================
	
	@RequestMapping(value = UrlConstants.User.ACTIVATE_USER, method = RequestMethod.GET)
	public @ResponseBody Response activateUser(@RequestParam Long userDetailsId, @RequestParam String authCode) throws UMException {
		String logTag = "activateUser(): ";
		log.info("Entering into : "+logTag+ "with authCode: "+authCode+", userDetailsId: "+userDetailsId);
		UserAuthDetails userAuthDetails = null;
		
		try {
			userAuthDetails = authenticationService.getUserAuthDetails(authCode);
			
			if(userAuthDetails.isValidAuthCode()) {
				if(Constants.SUPER_USER.compareTo(userAuthDetails.getRoleId()) == 0) {
					return userService.activateUser(userDetailsId);
				} else {
					return getInvalidAccessResponse();
				}
			} else {
				return getInvalidAuthCodeResponse();
			}
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while checking the authCode existance or when trying to check the active user."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
	}
	
	//========================================================================
	
	@RequestMapping(value=UrlConstants.User.PHOTO_UPLOAD, method = RequestMethod.POST)
	public @ResponseBody Response upload(@RequestParam CommonsMultipartFile userPhoto, @RequestParam Long userDetailsId,
			@RequestParam String authCode) throws UMException {
		String logTag = "upload(): ";
		log.info("Entering into : "+logTag+ "with authCode: "+authCode);
		UserAuthDetails userAuthDetails = null;
		
		try {
			userAuthDetails = authenticationService.getUserAuthDetails(authCode);
			
			if(userAuthDetails.isValidAuthCode()) {
				if(Constants.SUPER_USER.compareTo(userAuthDetails.getRoleId()) == 0) {
					return userService.upload(userPhoto,userDetailsId);
				} else {
					return getInvalidAccessResponse();
				}
			} else {
				return getInvalidAuthCodeResponse();
			}
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while checking the authCode existance or when trying to updaload the photo"+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
	}

	//========================================================================
	
	@RequestMapping(value = UrlConstants.User.LOGOUT, method = RequestMethod.POST)
	public @ResponseBody Response userLogout(@RequestParam String authCode) throws UMException {
		String logTag = "userLogout(): ";
		log.info("Entering into : "+logTag+ "with authCode: "+authCode);
		try {
			return authenticationService.logoutUser(authCode);
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while logout the user."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
	}

	//========================================================================
	
	@RequestMapping(value = UrlConstants.User.CHANGE_PASSWORD, method = RequestMethod.POST)
	public @ResponseBody Response changePassword(@RequestBody ChangePassword changePassword, @RequestParam String authCode) throws UMException {
		String logTag = "changePassword(): ";
		log.info("Entering into : "+logTag+ "with authCode: "+authCode);
		UserAuthDetails userAuthDetails = null;
		
		try {
			userAuthDetails = authenticationService.getUserAuthDetails(authCode);
			
			if(userAuthDetails.isValidAuthCode()) {
				return userService.changePassword(changePassword, userAuthDetails.getUserDetailsId());
			} else {
				return getInvalidAuthCodeResponse();
			}
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while change the password."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
	}
	
	//=======================================================================
	
	@RequestMapping(value = UrlConstants.User.FIRST_TIME_LOGIN_CHANGE_PASSWORD, method = RequestMethod.POST)
	public @ResponseBody Response firstTimeLoginChangePassword(@RequestBody ChangePassword changePassword, @RequestParam String authCode) throws UMException {
		String logTag = "firstTimeLoginChangePassword(): ";
		log.info("Entering into : "+logTag+ "with authCode: "+authCode);
		UserAuthDetails userAuthDetails = null;
		
		try {
			userAuthDetails = authenticationService.getUserAuthDetails(authCode);
			
			if(userAuthDetails.isValidAuthCode()) {
				return userService.firstTimeLoginChangePassword(changePassword, userAuthDetails.getUserDetailsId());
			} else {
				return getInvalidAuthCodeResponse();
			}
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while change the password."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
	}
	
	//========================================================================

	@RequestMapping(value = UrlConstants.User.RESET_PASSWORD, method = RequestMethod.POST)
	public @ResponseBody Response resetPassword( @RequestParam String authCode , @RequestParam Long userDetailsId) throws UMException{
		String logTag = "resetPassword(): ";
		log.info("Entering into : "+logTag+ "with authCode: "+authCode);
		UserAuthDetails userAuthDetails = null;
		try {
			userAuthDetails = authenticationService.getUserAuthDetails(authCode);
			
			if(userAuthDetails.isValidAuthCode()) {
				return userService.resetPassword(userDetailsId);
			} else {
				return getInvalidAuthCodeResponse();
			}
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while reseting the password."+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		
	}
	
	//========================================================================
	
	@RequestMapping(value = UrlConstants.User.SECONDARY_AUTHORIZATION_DETAILS_QUESITIONS_GET, method = RequestMethod.POST)
	public @ResponseBody Response getSecondaryAuthorizationDetailsQuesions() throws UMException{
		String logTag = "getSecondaryAuthorizationDetailsQuesions(): ";
		log.info("Entering into : "+logTag);
		try {
			return userService.getSecondaryAuthorizationDetailsQuesions();
		} catch (Exception e) {
			String errMessage = logTag +"Exception occurred while retrieving secondary authorization details"+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
		
	}
	
	//============================================================================
	@RequestMapping(value = UrlConstants.User.USER_SECONDARY_AUTHORIZATION_DETAILS_GET, method = RequestMethod.POST)
	public @ResponseBody Response getUserSecondaryAuthorizationDetails(@RequestParam String authCode,
			@RequestParam Long userDetailsId) throws UMException {
		String logTag = "getUserSecondaryAuthorizationDetails(): ";
		log.info("Entering into : " + logTag + "with authCode: " + authCode);
		UserAuthDetails userAuthDetails = null;
		try {
			userAuthDetails = authenticationService.getUserAuthDetails(authCode);

			if (userAuthDetails.isValidAuthCode()) {
				return userService.getUserSecondaryAuthorizationDetails(userDetailsId);
			} else {
				return getInvalidAuthCodeResponse();
			}
		} catch (Exception e) {
			String errMessage = logTag + "Exception occurred while retrieving user secondary authorization details" + e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
	}

	// ============================================================================

	@RequestMapping(value = UrlConstants.User.USER_SECONDARY_AUTHORIZATIOND_DETAILS_SAVE, method = RequestMethod.POST)
	public @ResponseBody Response saveUserSecondaryAuthorizationDetails(
			@RequestBody UserSecondaryAuthorizationDetails userSecondaryAuthorizationDetails,
			@RequestParam String authCode) throws UMException {
		String logTag = "saveUserSecondaryAuthorizationDetails(): ";
		log.info("Entering into : " + logTag + "with authCode: " + authCode);
		UserAuthDetails userAuthDetails = null;
		try {
			userAuthDetails = authenticationService.getUserAuthDetails(authCode);

			if (userAuthDetails.isValidAuthCode()) {
				return userService.saveUserSecondaryAuthorizationDetails(userSecondaryAuthorizationDetails);
			} else {
				return getInvalidAuthCodeResponse();
			}
		} catch (Exception e) {
			String errMessage = logTag + "Exception occurred when trying to save user secondary authorization details "
					+ e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
	}

	// ============================================================================

	@RequestMapping(value = UrlConstants.User.USER_SECONDARY_AUTHORIZATIOND_DETAILS_UPDATE, method = RequestMethod.POST)
	public @ResponseBody Response updateUserSecondaryAuthorizationDetails(
			@RequestBody UserSecondaryAuthorizationDetails userSecondaryAuthorizationDetails,
			@RequestParam String authCode) throws UMException {
		String logTag = "updateUserSecondaryAuthorizationDetails(): ";
		log.info("Entering into : " + logTag + "with authCode: " + authCode);
		UserAuthDetails userAuthDetails = null;
		try {
			userAuthDetails = authenticationService.getUserAuthDetails(authCode);

			if (userAuthDetails.isValidAuthCode()) {
				return userService.updateUserSecondaryAuthorizationDetails(userSecondaryAuthorizationDetails);
			} else {
				return getInvalidAuthCodeResponse();
			}
		} catch (Exception e) {
			String errMessage = logTag
					+ "Exception occurred when trying to update user secondary authorization details " + e;
			log.error(errMessage, e);
			throw new UMException(errMessage, e);
		}
	}
}