
package com.sciits.devops.poc.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_auth_code_details")
public class UserAuthCodeDetails extends BaseData  {
	private static final long serialVersionUID = 4351672344043604871L;
	@Id
	@GeneratedValue
	@Column(name = "user_auth_code_details_id", length = 20, nullable = false)
	private Long userAuthId;
	
	/*@Column(name = "user_details_id", length = 20, nullable = false)
	private Long userDetailsId;*/
	@OneToOne
	UserDetails userDetails;
	
	@Column(name = "auth_code", length = 32, nullable = false)
	private String authCode;
	
	@Column(name = "status", length = 1, nullable = false)
	private String status;

	@Column(name = "login_timestamp", nullable = false)
	private Date loginTimestamp;
	
	@Column(name = "logout_timestamp", nullable = false)
	private Date logoutTimestamp;

	public UserAuthCodeDetails() {}
	
	public UserAuthCodeDetails(Long userAuthId, /*Long userDetailsId,*/ String authCode, String status, 
		Date loginTimestamp, Date logoutTimestamp) {
		super();
		this.userAuthId = userAuthId;
		/*this.userDetailsId = userDetailsId;*/
		this.authCode = authCode;
		this.status = status;
		this.loginTimestamp = loginTimestamp;
		this.logoutTimestamp = logoutTimestamp;
	}

	/**
	 * @return the userAuthId
	 */
	public Long getUserAuthId() {
		return userAuthId;
	}

	/**
	 * @param userAuthId the userAuthId to set
	 */
	public void setUserAuthId(Long userAuthId) {
		this.userAuthId = userAuthId;
	}

	/**
	 * @return the userDetailsId
	 *//*
	public Long getUserDetailsId() {
		return userDetailsId;
	}

	*//**
	 * @param userDetailsId the userDetailsId to set
	 *//*
	public void setUserDetailsId(Long userDetailsId) {
		this.userDetailsId = userDetailsId;
	}*/

	
	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		return authCode;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	/**
	 * @param authCode the authCode to set
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the loginTimestamp
	 */
	public Date getLoginTimestamp() {
		return loginTimestamp;
	}

	/**
	 * @param loginTimestamp the loginTimestamp to set
	 */
	public void setLoginTimestamp(Date loginTimestamp) {
		this.loginTimestamp = loginTimestamp;
	}

	/**
	 * @return the logoutTimestamp
	 */
	public Date getLogoutTimestamp() {
		return logoutTimestamp;
	}

	/**
	 * @param logoutTimestamp the logoutTimestamp to set
	 */
	public void setLogoutTimestamp(Date logoutTimestamp) {
		this.logoutTimestamp = logoutTimestamp;
	}
}