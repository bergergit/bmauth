package com.bergermobile.rest.domain;

import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the role database table.
 * 
 */
public class RoleRest extends BaseTableRest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer roleId;

	private String roleName;

	private ApplicationRest applicationRest;

	private List<UserRoleRest> userRolesRest;

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

	public ApplicationRest getApplicationRest() {
		return applicationRest;
	}

	public void setApplicationRest(ApplicationRest applicationRest) {
		this.applicationRest = applicationRest;
	}

	public List<UserRoleRest> getUserRolesRest() {
		return userRolesRest;
	}

	public void setUserRolesRest(List<UserRoleRest> userRolesRest) {
		this.userRolesRest = userRolesRest;
	}

}