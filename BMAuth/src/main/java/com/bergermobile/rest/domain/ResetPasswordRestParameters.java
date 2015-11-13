package com.bergermobile.rest.domain;

import org.hibernate.validator.constraints.NotBlank;

public class ResetPasswordRestParameters {

	@NotBlank
	String password;
	
	@NotBlank
	String token;
	
	@NotBlank
	Integer userId;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public boolean isTokenValid() {

		return false;
	}

}
