//public class UserRepositoryTestHibernate {

package com.bergermobile.persistence.repository;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bergermobile.BmAuthApplication;
import com.bergermobile.persistence.domain.ContractUser;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class UserRepositoryTestHibernate {

	@Autowired
	private OnlineContractRepository onlineContractRepository;
/*
	@Test
	public void testThatGetOnlineContract() {

		System.out.println("Inicio: 1, 2");
		ContractUser tem = onlineContractRepository.findByUserIdAndApplicationId(1, 2);
		assertEquals(tem, null);
		
		System.out.println("Inicio: 2, 2");
		tem = onlineContractRepository.findByUserIdAndApplicationId(2, 2);
		assertNotNull(tem);

		System.out.println("Inicio: 3, 2");
		tem = onlineContractRepository.findByUserIdAndApplicationId(3, 2);
		assertNotNull(tem);
		
		System.out.println("Inicio: 4, 2");
		tem = onlineContractRepository.findByUserIdAndApplicationId(4, 2);
		assertEquals(tem, null);

	}
*/
}
