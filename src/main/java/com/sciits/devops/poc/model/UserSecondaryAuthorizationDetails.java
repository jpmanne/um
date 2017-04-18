package com.sciits.devops.poc.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "user_secondary_authorization_details")
public class UserSecondaryAuthorizationDetails extends BaseData {
	private static final long serialVersionUID = 1006823931720428762L;
	@Id
	@GeneratedValue
	@Column(name = "user_secondary_auth_details_id", length = 20, nullable = false)
	private Long userSecondaryAuthDetailsId;
	
	@Column(name = "user_details_id", length = 20, nullable = false ,unique= true)
	private Long userDetailsId;
	
	@Column(name = "first_quesition_id", length = 20, nullable = false)
	private Long firstQuesitiondId ;

	@Column(name = "first_quesition_answer", length = 150, nullable = false)
	private String firstQuesitionAnswer;

	@Column(name = "second_quesition_id", length = 20, nullable = false)
	private Long secondQuesitiondId ;

	@Column(name = "second_quesition_answer", length = 150, nullable = false)
	private String secondQuesitionAnswer;
	
	@Column(name = "login_timestamp", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date loginTimestamp;
	
	@Column(name = "status", length = 1, nullable = false)
	private String status;
	
	public Long getUserSecondaryAuthDetailsId() {
		return userSecondaryAuthDetailsId;
	}

	public void setUserSecondaryAuthDetailsId(Long userSecondaryAuthDetailsId) {
		this.userSecondaryAuthDetailsId = userSecondaryAuthDetailsId;
	}

	public Long getUserDetailsId() {
		return userDetailsId;
	}

	public void setUserDetailsId(Long userDetailsId) {
		this.userDetailsId = userDetailsId;
	}

	public Long getFirstQuesitiondId() {
		return firstQuesitiondId;
	}

	public void setFirstQuesitiondId(Long firstQuesitiondId) {
		this.firstQuesitiondId = firstQuesitiondId;
	}

	public String getFirstQuesitionAnswer() {
		return firstQuesitionAnswer;
	}

	public void setFirstQuesitionAnswer(String firstQuesitionAnswer) {
		this.firstQuesitionAnswer = firstQuesitionAnswer;
	}

	public Long getSecondQuesitiondId() {
		return secondQuesitiondId;
	}

	public void setSecondQuesitiondId(Long secondQuesitiondId) {
		this.secondQuesitiondId = secondQuesitiondId;
	}

	public String getSecondQuesitionAnswer() {
		return secondQuesitionAnswer;
	}

	public void setSecondQuesitionAnswer(String secondQuesitionAnswer) {
		this.secondQuesitionAnswer = secondQuesitionAnswer;
	}

	public Date getLoginTimestamp() {
		return loginTimestamp;
	}

	public void setLoginTimestamp(Date loginTimestamp) {
		this.loginTimestamp = loginTimestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserSecondaryAuthorizationDetails(){
		
	} 
	
	/**
	 * @param userSecondaryAuthDetailsId
	 * @param userDetailsId
	 * @param firstQuesitiondId
	 * @param firstQuesitionAnswer
	 * @param secondQuesitiondId
	 * @param secondQuesitionAnswer
	 * @param loginTimestamp
	 * @param status
	 */
	public UserSecondaryAuthorizationDetails(Long userSecondaryAuthDetailsId,Long userDetailsId,Long firstQuesitiondId,String firstQuesitionAnswer,Long secondQuesitiondId,String secondQuesitionAnswer,Date loginTimestamp,String status ){
		this.userSecondaryAuthDetailsId = userSecondaryAuthDetailsId;
		this.userDetailsId = userDetailsId;
		this.firstQuesitiondId = firstQuesitiondId;
		this.firstQuesitionAnswer = firstQuesitionAnswer;
		this.secondQuesitiondId = secondQuesitiondId;
		this.secondQuesitionAnswer = secondQuesitionAnswer;
		this.loginTimestamp = loginTimestamp;
		this.status = status;
	}
		
}
