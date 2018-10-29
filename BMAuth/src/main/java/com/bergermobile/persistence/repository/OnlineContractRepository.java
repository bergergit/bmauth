package com.bergermobile.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bergermobile.persistence.domain.Application;
import com.bergermobile.persistence.domain.OnlineContract;

public interface OnlineContractRepository extends CrudRepository<OnlineContract, Integer> {

	public OnlineContract findByOnlineContractId(Integer onlineContractId);

	public List<OnlineContract> findByApplication(Application application);

	public List<OnlineContract> findAll();
	
	@Query("select oc from OnlineContract oc where oc.application = :application order by oc.contractVersion DESC")
	public List<OnlineContract> getLatestByApplication(@Param("application") Application application);

}
