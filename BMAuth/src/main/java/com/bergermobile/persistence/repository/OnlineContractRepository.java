package com.bergermobile.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bergermobile.persistence.domain.Application;
import com.bergermobile.persistence.domain.OnlineContract;

public interface OnlineContractRepository extends CrudRepository<OnlineContract, Integer> {

	public OnlineContract findByOnlineContractId(Integer onlineContractId);
	
	public List<OnlineContract> findByApplication(Application application);
	
}
