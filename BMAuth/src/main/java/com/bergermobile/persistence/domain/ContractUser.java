package com.bergermobile.persistence.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the contract_user database table.
 * 
 */
@Entity
@Table(name="contract_user")
public class ContractUser extends BaseTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="contract_user_id")
	private Integer contractUserId;

	@Lob
	private String headers;

	private String ip;

	@Column(name="signed_date")
	private Timestamp signedDate;

	//bi-directional many-to-one association to OnlineContract
	@ManyToOne
	@JoinColumn(name="online_contract_online_contract_id")
	private OnlineContract onlineContract;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public ContractUser() {
	}

	public Integer getContractUserId() {
		return this.contractUserId;
	}

	public void setContractUserId(Integer contractUserId) {
		this.contractUserId = contractUserId;
	}


	public String getHeaders() {
		return this.headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Timestamp getSignedDate() {
		return this.signedDate;
	}

	public void setSignedDate(Timestamp signedDate) {
		this.signedDate = signedDate;
	}

	public OnlineContract getOnlineContract() {
		return this.onlineContract;
	}

	public void setOnlineContract(OnlineContract onlineContract) {
		this.onlineContract = onlineContract;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}