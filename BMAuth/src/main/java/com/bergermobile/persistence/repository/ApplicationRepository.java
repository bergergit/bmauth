package com.bergermobile.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bergermobile.persistence.domain.Application;

public interface ApplicationRepository extends CrudRepository<Application, Integer> {

	public Application findByApplicationId(Integer applicationId);
	
	public List<Application> findByApplicationName(String applicationName);
}
