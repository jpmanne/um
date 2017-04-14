package com.sciits.devops.poc.model;

import java.io.Serializable;

public class UserAuthDetails implements Serializable {
	private static final long serialVersionUID = -2908136203222183404L;
	private boolean isValidAuthCode = false;
	private Long userDetailsId;
	private Long roleId;
	public boolean isValidAuthCode() {
		return isValidAuthCode;
	}
	public void setValidAuthCode(boolean isValidAuthCode) {
		this.isValidAuthCode = isValidAuthCode;
	}
	public Long getUserDetailsId() {
		return userDetailsId;
	}
	public void setUserDetailsId(Long userDetailsId) {
		this.userDetailsId = userDetailsId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
