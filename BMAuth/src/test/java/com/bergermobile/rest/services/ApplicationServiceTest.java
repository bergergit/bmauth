package com.bergermobile.rest.services;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
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
 @TransactionConfiguration(defaultRollback = true)
 @ActiveProfiles("dev")
public class ApplicationServiceTest {

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Test
	public void testIfFindAllReturnsAllApplications() {

		applicationRepository.save(PersistenceFixture.megaFunkSystem());

		List<ApplicationRest> applicationList = applicationService.findAllApplications();

		assertEquals(applicationList.size(), 1);

	}

	@Test
	public void testIfSaveWorks() {

		ApplicationRest applicationRest = ConversionUtilities
				.applicationToApplicationRest(PersistenceFixture.megaFunkSystem());

		try{
		applicationService.save(applicationRest);
		fail("Duplicate key - It shouldn't allow to have 2 Applications with same name");
		} catch(DataIntegrityViolationException e) {

		}

		
		
//		List<ApplicationRest> applicationList = applicationService.findAllApplications();
//		assertEquals(applicationList.size(), 1);

	}

}
