package com.bergermobile.persistence.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user")
public class User extends BaseTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Integer userId;

	private Boolean active;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(name = "document_number")
	private String documentNumber;

	private String email;

	@Column(name = "login_type")
	private Short loginType;

	private String name;

	private String password;

	@Column(name = "user_type")
	private Short userType;

	private String username;

	// bi-directional many-to-one association to ContractUser
	@OneToMany(mappedBy = "user")
	private List<ContractUser> contractUsers;

	// bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy = "user", fetch=FetchType.EAGER, cascade={CascadeType.MERGE}, orphanRemoval=true)
	private List<UserRole> userRoles;

	public User() {
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDocumentNumber() {
		return this.documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Short getLoginType() {
		return this.loginType;
	}

	public void setLoginType(Short loginType) {
		this.loginType = loginType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Short getUserType() {
		return this.userType;
	}

	public void setUserType(Short userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<ContractUser> getContractUsers() {
		return this.contractUsers;
	}

	public void setContractUsers(List<ContractUser> contractUsers) {
		this.contractUsers = contractUsers;
	}

	public ContractUser addContractUser(ContractUser contractUser) {
		getContractUsers().add(contractUser);
		contractUser.setUser(this);

		return contractUser;
	}

	public ContractUser removeContractUser(ContractUser contractUser) {
		getContractUsers().remove(contractUser);
		contractUser.setUser(null);

		return contractUser;
	}

	public List<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public UserRole addUserRole(UserRole userRole) {
		getUserRoles().add(userRole);
		userRole.setUser(this);

		return userRole;
	}

	public UserRole removeUserRole(UserRole userRole) {
		getUserRoles().remove(userRole);
		userRole.setUser(null);

		return userRole;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", active=" + active + ", email="
				+ email + ", loginType=" + loginType + ", name=" + name
				+ ", username=" + username + "]";
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