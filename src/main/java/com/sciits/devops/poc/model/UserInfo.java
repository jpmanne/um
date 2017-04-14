package com.sciits.devops.poc.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UserInfo {
	private String userName;
	private String password;
	private CommonsMultipartFile commonsMultipartFile;
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the commonsMultipartFile
	 */
	public CommonsMultipartFile getCommonsMultipartFile() {
		return commonsMultipartFile;
	}
	/**
	 * @param commonsMultipartFile the commonsMultipartFile to set
	 */
	public void setCommonsMultipartFile(CommonsMultipartFile commonsMultipartFile) {
		this.commonsMultipartFile = commonsMultipartFile;
	}
	/**
	 * @param userName
	 * @param password
	 * @param commonsMultipartFile
	 */
	public UserInfo(String userName, String password, CommonsMultipartFile commonsMultipartFile) {
		super();
		this.userName = userName;
		this.password = password;
		this.commonsMultipartFile = commonsMultipartFile;
	}
	/**
	 * 
	 */
	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
