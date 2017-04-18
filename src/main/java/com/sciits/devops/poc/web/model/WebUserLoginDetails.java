package com.sciits.devops.poc.web.model;

public class WebUserLoginDetails {

	private Long userDetailsId;
	private String firstName;
	private String middleName;
	private String lastName;
	private Long roleId;
	private String authCode;
	private String isSecondaryAuthorizationSaved;
	private String isDefaultPasswordChanged;
	
	public WebUserLoginDetails() {
	}

	/**
	 * @return the userDetailsId
	 */
	public Long getUserDetailsId() {
		return userDetailsId;
	}

	/**
	 * @param userDetailsId
	 *            the userDetailsId to set
	 */
	public void setUserDetailsId(Long userDetailsId) {
		this.userDetailsId = userDetailsId;
	}

	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		return authCode;
	}

	/**
	 * @param authCode
	 *            the authCode to set
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName
	 *            the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getIsSecondaryAuthorizationSaved() {
		return isSecondaryAuthorizationSaved;
	}

	public void setIsSecondaryAuthorizationSaved(String isSecondaryAuthorizationSaved) {
		this.isSecondaryAuthorizationSaved = isSecondaryAuthorizationSaved;
	}

	public String getIsDefaultPasswordChanged() {
		return isDefaultPasswordChanged;
	}

	public void setIsDefaultPasswordChanged(String isDefaultPasswordChanged) {
		this.isDefaultPasswordChanged = isDefaultPasswordChanged;
	}
}
