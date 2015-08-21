package com.bergermobile.rest.domain;

public enum LoginTypeRest {

	FACEBOOK((short) 1, "Facebook"), GOOGLE_PLUS((short) 2, "Google +"), INTERNO((short) 3, "Login Interno");

	private final short loginTypeValue;
	private final String description;

	LoginTypeRest(short loginTypeValue, String description) {

		this.loginTypeValue = loginTypeValue;
		this.description = description;

	}

	public short getValue() {

		return this.loginTypeValue;
	}

	public String getDescription() {

		return this.description;
	}
	
}
