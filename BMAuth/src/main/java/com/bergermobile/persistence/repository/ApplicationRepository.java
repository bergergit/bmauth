package com.bergermobile.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.bergermobile.persistence.domain.Application;

public interface ApplicationRepository extends CrudRepository<Application, Integer> {

	public Application findByApplicationId(Integer applicationId);

	public Application findByApplicationName(String applicationName);
}
