package com.bergermobile.rest.domain;

public enum ActiveRest {

	YES((short) 1, "SIM"), NO((short) 0, "NAO");

	private final short typeValue;
	private final String description;

	ActiveRest(short typeValue, String description) {

		this.typeValue = typeValue;
		this.description = description;

	}

	public short getValue() {

		return this.typeValue;
	}

	public String getDescription() {

		return this.description;
	}

	
}
