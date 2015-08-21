package com.bergermobile.rest.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The persistent class for the config database table.
 * 
 */
public class ConfigRest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer configId;

	private String configCode;

	private BigDecimal numberValue;

	private String stringValue;

	public ConfigRest() {
	}

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	public BigDecimal getNumberValue() {
		return numberValue;
	}

	public void setNumberValue(BigDecimal numberValue) {
		this.numberValue = numberValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

}