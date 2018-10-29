package com.bergermobile.rest.functional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.bergermobile.BmAuthApplication;
import com.bergermobile.persistence.domain.Application;
import com.bergermobile.persistence.domain.fixture.PersistenceFixture;
import com.bergermobile.persistence.repository.ApplicationRepository;
import com.bergermobile.rest.controller.ApplicationQueryController;
import com.bergermobile.rest.domain.ApplicationRest;
import com.bergermobile.rest.services.RestConversionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
@Transactional
@Rollback(true)
@ActiveProfiles("dev")
public class ApplicationControllerFunctionalTest {

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	ObjectMapper mapper;

	MockMvc mockMvc;

	@InjectMocks
	ApplicationQueryController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		// mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void applicationQueryControllerReturnJsonCorrectly() throws Exception {

		applicationRepository.save(PersistenceFixture.bmStreamingSystem());

		MvcResult result = mockMvc.perform(get("/bmauth/applications")).andDo(print()).andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();

		assertTrue(content.contains("Mega Funk"));

	}

	@Test
	public void applicationQueryControllerReturnApplicationIsNotFound() throws Exception {

		MvcResult result = mockMvc.perform(get("/bmauth/applications/{id}", 9999)).andDo(print())
				.andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();

		assertEquals(content, "");

	}

	 @Test
	 public void jsonDeleteApplicationIsNotFound() throws Exception {
	
	 mockMvc.perform(delete("/bmauth/applications/{id}",99)).andExpect(status().isNotFound());
	
	 }

	@Test
	public void jsonPostCreatesApplicationCorrectly() throws Exception {
		ApplicationRest applicationRest = RestConversionService
				.applicationToApplicationRest(PersistenceFixture.bmStreamingSystem());

		mockMvc.perform(post("/bmauth/applications").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(applicationRest))).andExpect(status().isCreated());

		Application application = applicationRepository.findByApplicationName("Mega Funk");

		assertEquals(application.getApplicationName(), "Mega Funk");

	}

	@Test
	public void jsonDeleteApplicationCorrectly() throws Exception {

		applicationRepository.save(PersistenceFixture.bmStreamingSystem());

		Application application = applicationRepository.findByApplicationName("Mega Funk");

		assertEquals(application.getApplicationName(), "Mega Funk");

		mockMvc.perform(delete("/bmauth/applications/{id}", application.getApplicationId())).andExpect(status().isOk());

		application = applicationRepository.findByApplicationName("Mega Funk");

		assertEquals(application, null);

	}

}
