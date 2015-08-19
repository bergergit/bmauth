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
public class ContractUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="contract_user_id")
	private int contractUserId;

	@Column(name="created_by")
	private int createdBy;

	@Column(name="creation_date")
	private Timestamp creationDate;

	@Lob
	private String headers;

	private String ip;

	@Column(name="last_update_date")
	private Timestamp lastUpdateDate;

	@Column(name="last_updated_by")
	private int lastUpdatedBy;

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

	public int getContractUserId() {
		return this.contractUserId;
	}

	public void setContractUserId(int contractUserId) {
		this.contractUserId = contractUserId;
	}

	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
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

	public Timestamp getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public int getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(int lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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