package com.bergermobile.rest.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bergermobile.persistence.domain.Application;
import com.bergermobile.persistence.repository.ApplicationRepository;
import com.bergermobile.rest.domain.ApplicationRest;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;

	@Override
	public List<ApplicationRest> findAllApplications() {

		// Get All Application
		List<ApplicationRest> applicationRestList = new ArrayList<ApplicationRest>();

		// for each Applications
		for (Application application : applicationRepository.findAll()) {

			ApplicationRest applicationRest = new ApplicationRest();

			applicationRest = ConversionUtilities.applicationToApplicationRest(application);

			applicationRestList.add(applicationRest);

		}

		return applicationRestList;

	}

	@Override
	public ApplicationRest findByApplicationId(int applicationId) {

		Application application = applicationRepository.findByApplicationId(applicationId);

		if (application != null) {

			return ConversionUtilities.applicationToApplicationRest(application);

		}

		return null;

	}

	@Override
	public ApplicationRest findByApplicationName(String applicationName) {

		Application application = applicationRepository.findByApplicationName(applicationName);

		if (application != null) {
			
			return ConversionUtilities.applicationToApplicationRest(application);

		}

		return null;

	}

	@Override
	public void save(ApplicationRest applicationRest) {

		Application application = ConversionUtilities.applicationRestToApplication(applicationRest);
		
		applicationRepository.save(application);
	}

	@Override
	public void delete(int applicationId) {

		applicationRepository.delete(applicationId);

	}

}
