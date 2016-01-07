package com.bergermobile.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bergermobile.persistence.domain.ContractUser;

public interface ContractUserRepository extends CrudRepository<ContractUser, Integer> {

	//@Query("select true from ContractUser as cu where cu.user.userId = :userId and cu.onlineContract = (select max(oc.onlineContractId) from OnlineContract as oc where oc.application.mandatoryContract = 1 and oc.application.applicationId = :applicationId order by oc.onlineContractId)")
	//public boolean findByUserIdAndApplicationId(@Param("userId") Integer userId, @Param("applicationId") Integer applicationId);

}
