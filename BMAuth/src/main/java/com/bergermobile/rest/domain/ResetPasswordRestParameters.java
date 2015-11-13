package com.bergermobile.rest.domain;

import org.neo4j.cypher.internal.compiler.v2_1.planner.logical.steps.optional;

public class ResetPasswordRestParameters {

	String password;
	String password2;
	String token;
	Integer userId;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public boolean isValidPassword() {

		if (this.password.equals(password2) && this.password.toString() != null && this.password2.toString() != null) {
			return true;
		}

		return false;

	}

	public boolean isTokenValid() {

		return false;
	}

}
