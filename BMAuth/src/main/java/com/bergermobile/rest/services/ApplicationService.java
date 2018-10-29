package com.bergermobile.rest.services;

import java.util.List;

import com.bergermobile.rest.domain.ApplicationRest;

public interface ApplicationService {

	public List<ApplicationRest> findAllApplications();

	public ApplicationRest findByApplicationId(int applicationId);

	public ApplicationRest findByApplicationName(String applicationName);

	public void save(ApplicationRest applicationRest);

	public void delete(int applicationId);
	
	public List<ApplicationRest> findAppsByUserId(Integer userId);

}
