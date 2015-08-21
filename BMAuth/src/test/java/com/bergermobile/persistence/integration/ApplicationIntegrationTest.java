package com.bergermobile.persistence.integration;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bergermobile.BmAuthApplication;
import com.bergermobile.persistence.domain.Application;
import com.bergermobile.persistence.domain.fixture.PersistenceFixture;
import com.bergermobile.persistence.repository.ApplicationRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = false)
// @ActiveProfiles("dev")
public class ApplicationIntegrationTest {

	@Autowired
	private ApplicationRepository applicationRepository;

	// This test checks if the connection with database is working
	@Test
	public void testThatInsertWorks() {

		Application application = PersistenceFixture.megaFunkSystem();

		Application savedApplication = applicationRepository.save(application);

		assertNotNull(savedApplication);

		assertEquals(savedApplication.getApplicationName(), "Mega Funk");

	}

}
