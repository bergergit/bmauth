package com.bergermobile.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bergermobile.persistence.domain.LanguageContract;
import com.bergermobile.persistence.domain.OnlineContract;

public interface LanguageContractRepository extends CrudRepository<LanguageContract, Integer> {

	public List<LanguageContract> findByOnlineContract(OnlineContract onlineContract);

	public List<LanguageContract> findAll();
}
