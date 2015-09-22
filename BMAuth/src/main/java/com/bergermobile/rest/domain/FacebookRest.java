package com.bergermobile.rest.domain;


/**
 * Reflects Facebook response object
 * 
 * @author fabioberger
 *
 */
public class FacebookRest {

	private String appId;
	private AuthResponse authResponse;
	private String status;
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public AuthResponse getAuthResponse() {
		return authResponse;
	}

	public void setAuthResponse(AuthResponse authResponse) {
		this.authResponse = authResponse;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public class AuthResponse {
		private String accessToken;
		private long expiresIn;
		private String signedRequest;
		private String userID;

		public String getAccessToken() {
			return accessToken;
		}

		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}

		public long getExpiresIn() {
			return expiresIn;
		}

		public void setExpiresIn(long expiresIn) {
			this.expiresIn = expiresIn;
		}

		public String getSignedRequest() {
			return signedRequest;
		}

		public void setSignedRequest(String signedRequest) {
			this.signedRequest = signedRequest;
		}

		public String getUserID() {
			return userID;
		}

		public void setUserID(String userID) {
			this.userID = userID;
		}

	}

}
