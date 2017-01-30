package com.bergermobile.persistence.integration;

import static com.bergermobile.persistence.domain.fixture.JPAAssertions.assertTableExists;
import static com.bergermobile.persistence.domain.fixture.JPAAssertions.assertTableHasColumn;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bergermobile.BmAuthApplication;

/**
 * Test if JPA Mappings are ok for the schema
 * 
 * @author fabioberger
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
@Transactional
@Rollback(true)
public class MappingIntegrationTest {

	@Autowired
	EntityManager manager;

	/**
	 * Empty method - if this passes, it means Spring context was loaded and
	 * properly connected to MySQL DB
	 */
	@Test
	public void contextWorks() {
	}

	@Test
	public void tablesExist() throws Exception {
		assertTableExists(manager, "config");
		assertTableExists(manager, "contract_user");
		assertTableExists(manager, "language_contract");
		assertTableExists(manager, "log");
		assertTableExists(manager, "online_contract");
		assertTableExists(manager, "role");
		assertTableExists(manager, "application");
		assertTableExists(manager, "user");
		assertTableExists(manager, "user_role");
	}

	/**
	 * Not verifying all columns... just the 'not null ones
	 * 
	 * @throws Exception
	 */
	@Test
	public void tablesHaveRequiredColumns() throws Exception {

		assertTableHasColumn(manager, "config", "config_id");
		assertTableHasColumn(manager, "config", "config_code");
		assertTableHasColumn(manager, "config", "number_value");
		assertTableHasColumn(manager, "config", "string_value");

		assertTableHasColumn(manager, "log", "log_id");
		assertTableHasColumn(manager, "log", "log_record_type");
		assertTableHasColumn(manager, "log", "record_id");
		assertTableHasColumn(manager, "log", "description_log");
		assertTableHasColumn(manager, "log", "creation_date");
		assertTableHasColumn(manager, "log", "created_by");
		assertTableHasColumn(manager, "log", "last_update_date");
		assertTableHasColumn(manager, "log", "last_updated_by");

		assertTableHasColumn(manager, "contract_user", "contract_user_id");
		assertTableHasColumn(manager, "contract_user", "signed_date");
		assertTableHasColumn(manager, "contract_user", "online_contract_online_contract_id");
		assertTableHasColumn(manager, "contract_user", "user_user_id");
		assertTableHasColumn(manager, "contract_user", "ip");
		assertTableHasColumn(manager, "contract_user", "headers");
		assertTableHasColumn(manager, "contract_user", "creation_date");
		assertTableHasColumn(manager, "contract_user", "created_by");
		assertTableHasColumn(manager, "contract_user", "last_update_date");
		assertTableHasColumn(manager, "contract_user", "last_updated_by");

		assertTableHasColumn(manager, "language_contract", "language_contract_id");
		assertTableHasColumn(manager, "language_contract", "language");
		assertTableHasColumn(manager, "language_contract", "html_contract");
		assertTableHasColumn(manager, "language_contract", "online_contract_online_contract_id");
		assertTableHasColumn(manager, "language_contract", "creation_date");
		assertTableHasColumn(manager, "language_contract", "created_by");
		assertTableHasColumn(manager, "language_contract", "last_update_date");
		assertTableHasColumn(manager, "language_contract", "last_updated_by");

		assertTableHasColumn(manager, "online_contract", "online_contract_id");
		assertTableHasColumn(manager, "online_contract", "contract_version");
		assertTableHasColumn(manager, "online_contract", "description");
		assertTableHasColumn(manager, "online_contract", "application_application_id");
		assertTableHasColumn(manager, "online_contract", "creation_date");
		assertTableHasColumn(manager, "online_contract", "created_by");
		assertTableHasColumn(manager, "online_contract", "last_update_date");
		assertTableHasColumn(manager, "online_contract", "last_updated_by");

		assertTableHasColumn(manager, "role", "role_id");
		assertTableHasColumn(manager, "role", "role_name");
		assertTableHasColumn(manager, "role", "application_application_id");
		assertTableHasColumn(manager, "role", "creation_date");
		assertTableHasColumn(manager, "role", "created_by");
		assertTableHasColumn(manager, "role", "last_update_date");
		assertTableHasColumn(manager, "role", "last_updated_by");

		assertTableHasColumn(manager, "application", "application_id");
		assertTableHasColumn(manager, "application", "application_name");
		assertTableHasColumn(manager, "application", "url");
		assertTableHasColumn(manager, "application", "test_mode");
		assertTableHasColumn(manager, "application", "mandatory_contract");
		assertTableHasColumn(manager, "application", "active");
		assertTableHasColumn(manager, "application", "creation_date");
		assertTableHasColumn(manager, "application", "created_by");
		assertTableHasColumn(manager, "application", "last_update_date");
		assertTableHasColumn(manager, "application", "last_updated_by");

		assertTableHasColumn(manager, "user", "user_id");
		assertTableHasColumn(manager, "user", "login_type");
		assertTableHasColumn(manager, "user", "username");
		assertTableHasColumn(manager, "user", "user_type");
		assertTableHasColumn(manager, "user", "document_number");
		assertTableHasColumn(manager, "user", "name");
		assertTableHasColumn(manager, "user", "email");
		assertTableHasColumn(manager, "user", "birthday");
		assertTableHasColumn(manager, "user", "password");
		assertTableHasColumn(manager, "user", "active");
		assertTableHasColumn(manager, "user", "creation_date");
		assertTableHasColumn(manager, "user", "created_by");
		assertTableHasColumn(manager, "user", "last_update_date");
		assertTableHasColumn(manager, "user", "last_updated_by");

		assertTableHasColumn(manager, "user_role", "user_role_id");
		assertTableHasColumn(manager, "user_role", "user_id");
		assertTableHasColumn(manager, "user_role", "role_id");
		assertTableHasColumn(manager, "user_role", "creation_date");
		assertTableHasColumn(manager, "user_role", "created_by");
		assertTableHasColumn(manager, "user_role", "last_update_date");
		assertTableHasColumn(manager, "user_role", "last_updated_by");

	}

}
