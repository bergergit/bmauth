package com.bergermobile.persistence.integration;

import static junit.framework.TestCase.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bergermobile.BmAuthApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
public class PasswordEncoderIntegration {
	
	@Autowired
    BCryptPasswordEncoder bcryptEncoder;
	
	@Test
	public void thatPasswordEncoderWorks() {
		assertTrue(bcryptEncoder.matches("###bmauth@@@", "$2a$10$mCmyaLcDSBYwQ9PYg2M/P.Q/Z2cHqDL9KQedSedOYNEQIZtFLBZjy"));
		//assertEquals("$2a$10$mCmyaLcDSBYwQ9PYg2M/P.Q/Z2cHqDL9KQedSedOYNEQIZtFLBZjy", encodedPass);
	}

}
