package com.bergermobile.rest.domain;

/**
 * Reflects Facebook response object
 * 
 * @author fabioberger
 *
 */
public class GoogleRest {

	private String clientId;
	private String accessToken;
	private String realm;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}


}
