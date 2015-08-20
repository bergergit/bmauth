package com.bergermobile.persistence.domain;

public enum UserType {

	CPF((short)1, "CPF"), CNPJ((short) 2, "CNPJ");

	private final short userTypeValue;
	private final String description;

	UserType(short userTypeValue, String description) {

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
