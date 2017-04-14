package com.sciits.devops.poc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="secondary_authorization_details")
public class SecondaryAuthorizationDetails extends BaseData  {
	private static final long serialVersionUID = -5290768205031639834L;

	@Id
	@GeneratedValue
	@Column(name = "secondary_auth_details_id", length = 20, nullable = false)
	private Long secondaryAuthDetailsId;
	
	@Column(name = "quesition", length = 32, nullable = false)
	private String quesition;

	@Column(name = "status", length = 1, nullable = false)
	private String status = "1";
	
	public Long getSecondaryAuthDetailsId() {
		return secondaryAuthDetailsId;
	}

	public void setSecondaryAuthDetailsId(Long secondaryAuthDetailsId) {
		this.secondaryAuthDetailsId = secondaryAuthDetailsId;
	}

	public String getQuesition() {
		return quesition;
	}

	public void setQuesition(String quesition) {
		this.quesition = quesition;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param secondaryAuthDetailsId
	 * @param quesition
	 * @param status
	 */
	
}
