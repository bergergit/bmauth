package com.bergermobile.rest.domain;

import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the role database table.
 * 
 */
public class RoleRest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer applicationId;

	private Integer roleId;

	private String roleName;

	// private List<UserRoleRest> userRolesRest;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}
	
	

}