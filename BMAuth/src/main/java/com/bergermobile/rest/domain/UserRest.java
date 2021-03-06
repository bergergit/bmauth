package com.bergermobile.rest.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.bergermobile.persistence.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The persistent class for the user database table.
 * 
 */
//@JsonIgnoreProperties(ignoreUnknown=true)
public class UserRest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer userId;

	private Boolean active;

	private Date birthday;

	private String documentNumber;

	@Email
	@NotBlank
	private String email;

	private Short loginType;
	
	@NotBlank
	private String name;

	private String password;

	private Short userType;

	private String username;

	private List<ContractUserRest> contractUsersRest;

	private List<RoleRest> userRolesRest;
	
	private String realm;
	
	/** 
	 * Intended to simply contain the RoleID with true values, for the roles the user belongs to
	 */
	private Map<Integer, Boolean> simpleUserRoles;
	
	/** 
	 * Intended to simply contain the ApplicationID with true values, for the roles the user belongs to
	 */
	//private Map<Integer, Boolean> simpleUserApplications;

	public UserRest() {
		userType = 0;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Short getLoginType() {
		return loginType != null ? loginType : 1;
	}
	
	/** 
	 * Convenience method that return the name of the login type
	 */
	public String getLoginTypeName() {
		return User.LoginType.values()[getLoginType() -1].getDescription();
	}

	public void setLoginType(Short loginType) {
		this.loginType = loginType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public Short getUserType() {
		return userType;
	}

	public void setUserType(Short userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<ContractUserRest> getContractUsersRest() {
		return contractUsersRest;
	}

	public void setContractUsersRest(List<ContractUserRest> contractUsersRest) {
		this.contractUsersRest = contractUsersRest;
	}

	public List<RoleRest> getUserRolesRest() {
		return userRolesRest;
	}

	public void setUserRolesRest(List<RoleRest> userRolesRest) {
		this.userRolesRest = userRolesRest;
	}
	
	public Map<Integer, Boolean> getSimpleUserRoles() {
		return simpleUserRoles;
	}

	public void setSimpleUserRoles(Map<Integer, Boolean> simpleUserRoles) {
		this.simpleUserRoles = simpleUserRoles;
	}
	
	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + userType;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	
	
	@Override
	public String toString() {
		return "UserRest [userId=" + userId + ", active=" + active + ", birthday=" + birthday + ", documentNumber="
				+ documentNumber + ", email=" + email + ", loginType=" + loginType + ", name=" + name + ", password="
				+ password + ", userType=" + userType + ", username=" + username + ", contractUsersRest="
				+ contractUsersRest + ", userRolesRest=" + userRolesRest + ", simpleUserRoles=" + simpleUserRoles + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRest other = (UserRest) obj;
		if (userType != other.userType)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public static enum LoginType {

		FACEBOOK((short) 1, "Facebook"), GOOGLE_PLUS((short) 2, "Google +"), INTERNAL((short) 3, "Internal Login");

		private final short loginTypeValue;
		private final String description;

		LoginType(short loginTypeValue, String description) {

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

	public static enum UserType {

		CPF((short) 1, "CPF"), CNPJ((short) 2, "CNPJ");

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

}
