package com.bergermobile.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BasicPersistenceFields {
	
	static Log LOG = LogFactory.getLog(BasicPersistenceFields.class);

	
	/**
	 * This joint point will intercept any save that is being made on an Entity, and will store the
	 * 4 basic fields that all tables have: creation_date, created_by, laste_update_date, last_updated_by
	 */
	@Before("com.bergermobile.aop.SystemArchitecture.saveRepositoryOperation()")
    public void saveBaseFields() {
        LOG.debug("*** Intercepting save() on repository layer ***");
        System.out.println("*** Intercepting save() on repository layer ***");
    }

   
}
