package com.bergermobile.persistence.integration;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.util.List;

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
import com.bergermobile.persistence.domain.OnlineContract;
import com.bergermobile.persistence.domain.fixture.PersistenceFixture;
import com.bergermobile.persistence.repository.ApplicationRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ActiveProfiles("dev")
public class ApplicationIntegrationTest {

	@Autowired
	private ApplicationRepository applicationRepository;

	// This test checks if the connection with database is working
	@Test
	public void testThatFindByApplicationIdWorks() {

		Application application = PersistenceFixture.megaFunkSystem();

		Application savedApplication = applicationRepository.save(application);

		Application findedApplication = applicationRepository.findByApplicationId(savedApplication.getApplicationId());

		assertNotNull(findedApplication);

		assertEquals(findedApplication.getApplicationName(), "Mega Funk");

		assertEquals(findedApplication.getRoles().size(), 4);

		List<OnlineContract> onlineContractList = findedApplication.getOnlineContracts();

		assertEquals(onlineContractList.size(), 2);

		int QuantityOfLanguages = 0;
		for (OnlineContract onlineContract : onlineContractList) {
			QuantityOfLanguages += onlineContract.getLanguageContract().size();
		}

		assertEquals(QuantityOfLanguages, 4);

	}

	@Test
	public void testThatInsertWorks() {

		Application application = PersistenceFixture.megaFunkSystem();

		Application savedApplication = applicationRepository.save(application);

		assertNotNull(savedApplication);

		assertEquals(savedApplication.getApplicationName(), "Mega Funk");

		assertEquals(savedApplication.getRoles().size(), 4);

		List<OnlineContract> onlineContractList = savedApplication.getOnlineContracts();

		assertEquals(onlineContractList.size(), 2);

		int QuantityOfLanguages = 0;
		for (OnlineContract onlineContract : onlineContractList) {
			QuantityOfLanguages += onlineContract.getLanguageContract().size();
		}

		assertEquals(QuantityOfLanguages, 4);

	}

	// This test checks if the connection with database is working
	@Test
	public void testThatFindByApplicationNameWorks() {

		Application application = PersistenceFixture.megaFunkSystem();

		applicationRepository.save(application);

		Application findedApplication = applicationRepository.findByApplicationName(application.getApplicationName());

		assertNotNull(findedApplication);

		assertEquals(findedApplication.getApplicationName(), "Mega Funk");

		assertEquals(findedApplication.getRoles().size(), 4);

		List<OnlineContract> onlineContractList = findedApplication.getOnlineContracts();

		assertEquals(onlineContractList.size(), 2);

		int QuantityOfLanguages = 0;
		for (OnlineContract onlineContract : onlineContractList) {
			QuantityOfLanguages += onlineContract.getLanguageContract().size();
		}

		assertEquals(QuantityOfLanguages, 4);

	}

	@Test
	public void testThatDeleteWorks() {

		Application application = PersistenceFixture.megaFunkSystem();

		Application savedApplication = applicationRepository.save(application);

		assertNotNull(savedApplication);

		assertEquals(
				applicationRepository.findByApplicationId(savedApplication.getApplicationId()).getApplicationName(),
				"Mega Funk");

		 applicationRepository.delete(savedApplication.getApplicationId());

		 Application findedApplication = applicationRepository.findByApplicationId(savedApplication.getApplicationId());

		 assertEquals(findedApplication, null);
		 
	}

}
