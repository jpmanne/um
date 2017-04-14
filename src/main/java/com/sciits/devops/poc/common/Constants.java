package com.sciits.devops.poc.common;

public class Constants {

	//LOCAL 
	public static final String DEPLOYMENT_FOLDER = "E:/UM/";

	public static final String UPLOADS_FOLDER = DEPLOYMENT_FOLDER + "UM_UPLOADS";
	public static final String REPORTS_FOLDER = UPLOADS_FOLDER + "/reports";
	public static final String ACTIVE = "1";
	public static final String INACTIVE = "0";
	public static final String DATE_FORMAT = "MM-dd-yyyy";
	public static final String DATE_FORMAT_DB = "YYYY-MM-DD";
	public static final String DATE_FORMAT_UI = "MM-dd-yyyy hh:mm";
	public static final String RFQ_FILES_LOCATION = UPLOADS_FOLDER + "/rfqs";

	public static final Long SUPER_USER = 1L;
	public static final Long STQ_USER_VES = 2L;
	public static final Long STQ_USER_FED = 3L;
	public static final Long GL_USER_VES = 4L;
	public static final Long GL_USER_FED = 5L;

	public static class User {
		public static final String USER_SIGNUP = "User registration is successful";
		public static final String USER_LOGIN = "User login is successful";
		public static final String USER_ACCOUNT_INACTIVE = "User Inactive";
		public static final String USER_LOGIN_FAILED = "User login failed.Please try again";
		public static final String USER_LIST = "Users list";
		public static final String USER_ID_NULL = "User id  is null";
		public static final String INVALID_USER_ID = "Invalid User Id";
		public static final String USER_DETAILS = "User details";
		public static final String SECONDARY_USER_DETAILS = "Secondary Authorization quesitions";
		public static final String USER_UPDATED = "User is updated successfully";
		public static final String USER_DELETED = "User deleted successfully";
		public static final String USER_PHOTO_ADDED = "Image Added Successsfully";
		public static final String USER_PHOTO_UPLOAD_FOLDER = UPLOADS_FOLDER + "/user_photos/";
		public static final String USER_ACTIVATED = "User activated successfully";
		public static final String USER_SECONDARY_AUTHORIZATION_DETAILS_SAVE = "User secondary authorization added successfully";
		public static final String USER_SECONDARY_AUTHORIZATION_DETAILS_UPDATE = "User secondary authorization updated successfully";
		public static final String USER_SECONDARY_AUTHORIZATION_DETAILS = "User secondary authorization details";
		public static final String USER_SECONDARY_AUTHORIZATIONS_DETAILS_NOT_SUMBMITED = "User not sumbited secondary authorization details";
		public static final String USER_DETAILS_NOT_EXIST = "User details not exist";
		public static final String SECONDARY_AUTHORIZATIONS_DETAILS_NOT_AVAILABLE_IN_DATABASE = "Secondary authorization details not evailable in database";
	}	
}