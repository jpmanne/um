
package com.sciits.devops.poc.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role_details")
public class RoleDetails extends BaseData {
	private static final long serialVersionUID = 5252093486377465535L;

	@Id
	@GeneratedValue
	@Column(name = "role_id", length = 20, nullable = false)
	private Long roleId;
	
	@Column(name = "role", length = 50, nullable = false)
	private String role;
	
	@Column(name = "status", length = 1, nullable = false)
	private String status;

	public RoleDetails() {}

	/**
	 * @param roleId
	 * @param role
	 * @param status
	 */
	public RoleDetails(Long roleId, String role, String status) {
		super();
		this.roleId = roleId;
		this.role = role;
		this.status = status;
	}

	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
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
}