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

//	@Query("select 1 from (select onlineContract from Application as app inner join OnlineContract as onlineContract where app.mandatoryContract = 1 and app.applicationId = :applicationId order by onlineContract.onlineContractId DESC LIMIT 1) as onlineContract left join ContractUser as contractUser inner join User as user where contractUser.contractUserId is null and user.userId = :userId")
//	public OnlineContract findByUserIdAndApplicationId(
//			@Param("userId") Integer userId,
//			@Param("applicationId") Integer applicationId
//			);

	@Query("select max(oc.online_contract_id) from OnlineContract as oc where oc.application.mandatoryContract = 1 and oc.application.applicationId = :applicationId order by oc.onlineContractId")
	public List<Integer> findByUserIdAndApplicationId(
	//		@Param("userId") Integer userId,
			@Param("applicationId") Integer applicationId
			);
	
}
