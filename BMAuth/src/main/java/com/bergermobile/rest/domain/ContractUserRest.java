package com.bergermobile.rest.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The persistent class for the contract_user database table.
 * 
 */
public class ContractUserRest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer contractUserId;

	private String headers;

	private String ip;

	private Timestamp signedDate;

	private OnlineContractRest onlineContractRest;

	private UserRest userRest;

	public ContractUserRest() {
	}

	public Integer getContractUserId() {
		return contractUserId;
	}

	public void setContractUserId(Integer contractUserId) {
		this.contractUserId = contractUserId;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Timestamp getSignedDate() {
		return signedDate;
	}

	public void setSignedDate(Timestamp signedDate) {
		this.signedDate = signedDate;
	}

	public OnlineContractRest getOnlineContractRest() {
		return onlineContractRest;
	}

	public void setOnlineContractRest(OnlineContractRest onlineContractRest) {
		this.onlineContractRest = onlineContractRest;
	}

	public UserRest getUserRest() {
		return userRest;
	}

	public void setUserRest(UserRest userRest) {
		this.userRest = userRest;
	}

}