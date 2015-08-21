package com.bergermobile.rest.domain;

import java.io.Serializable;

/**
 * The persistent class for the user_role database table.
 * 
 */
public class UserRoleRest extends BaseTableRest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer userRoleId;

	private RoleRest roleRest;

	private UserRest userRest;

	public Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public RoleRest getRoleRest() {
		return roleRest;
	}

	public void setRoleRest(RoleRest roleRest) {
		this.roleRest = roleRest;
	}

	public UserRest getUserRest() {
		return userRest;
	}

	public void setUserRest(UserRest userRest) {
		this.userRest = userRest;
	}

}