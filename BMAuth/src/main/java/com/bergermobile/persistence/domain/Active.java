package com.bergermobile.persistence.domain;

public enum Active {

	YES(true, "SIM"), NO(false, "NAO");

	private final boolean typeValue;
	private final String description;

	Active(boolean typeValue, String description) {

		this.typeValue = typeValue;
		this.description = description;

	}

	public boolean getValue() {

		return this.typeValue;
	}

	public String getDescription() {

		return this.description;
	}

}
