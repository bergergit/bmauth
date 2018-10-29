package com.bergermobile.rest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bergermobile.BmAuthApplication;
import com.bergermobile.persistence.domain.fixture.PersistenceFixture;
import com.bergermobile.persistence.repository.ApplicationRepository;
import com.bergermobile.rest.domain.ApplicationRest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
@Transactional
@Rollback(true)
@ActiveProfiles("dev")
public class ApplicationServiceTest {

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Test
	public void testIfFindAllReturnsAllApplications() {

		applicationRepository.save(PersistenceFixture.bmStreamingSystem());

		List<ApplicationRest> applicationList = applicationService.findAllApplications();

		// BMAuth application is added by default...
		assertEquals(2, applicationList.size());

	}

	@Test
	public void testIfDuplicateApplicationNameCheckWorks() {

		ApplicationRest applicationRest1 = RestConversionService
				.applicationToApplicationRest(PersistenceFixture.bmStreamingSystem());

		ApplicationRest applicationRest2 = RestConversionService
				.applicationToApplicationRest(PersistenceFixture.bmStreamingSystem());

		applicationService.save(applicationRest1);

		try {
			applicationService.save(applicationRest2);
			fail("Duplicate key - It shouldn't allow to have 2 Applications with same name");
		} catch (DataIntegrityViolationException e) {

		}

	}

	@Test
	public void testIfSaveWorks() {

		ApplicationRest applicationRest = RestConversionService
				.applicationToApplicationRest(PersistenceFixture.bmStreamingSystem());

		applicationService.save(applicationRest);

		List<ApplicationRest> applicationList = applicationService.findAllApplications();
		
		// BMAuth application is added by default...
		assertEquals(2, applicationList.size());

	}

	@Test
	public void testIfDeleteWorks() {

		ApplicationRest applicationRest = RestConversionService
				.applicationToApplicationRest(PersistenceFixture.bmStreamingSystem());

		applicationService.save(applicationRest);

		ApplicationRest application = applicationService.findByApplicationName("Mega Funk");
		assertEquals(application.getApplicationName(), applicationRest.getApplicationName());

		applicationService.delete(application.getApplicationId());

		application = applicationService.findByApplicationName("Mega Funk");
		assertEquals(application, null);

	}

}
