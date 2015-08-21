package com.bergermobile.rest.domain;

public enum UserTypeRest {

	CPF((short)1, "CPF"), CNPJ((short) 2, "CNPJ");

	private final short userTypeValue;
	private final String description;

	UserTypeRest(short userTypeValue, String description) {

		this.userTypeValue = userTypeValue;
		this.description = description;
		
	}

	public short getValue() {

		return this.userTypeValue;
	}

	public String getDescription() {

		return this.description;
	}

	
}
