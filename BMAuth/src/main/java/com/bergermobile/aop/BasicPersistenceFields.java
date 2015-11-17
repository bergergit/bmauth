package com.bergermobile.aop;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.bergermobile.persistence.domain.BaseTable;
import com.bergermobile.security.SecurityUser;

@Component
@Aspect
public class BasicPersistenceFields {
	
	static Log LOG = LogFactory.getLog(BasicPersistenceFields.class);
	
	@Autowired
	EntityManagerFactory emf;
	
	/**
	 * This joint point will intercept any save that is being made on an Entity, and will store the
	 * 4 basic fields that all tables have: creation_date, created_by, laste_update_date, last_updated_by
	 */
	@Before("com.bergermobile.aop.SystemArchitecture.saveRepositoryOperation() && args(baseTable,..)")
    public void saveBaseFields(BaseTable baseTable) {
        System.out.println("*** Intercepting save() on repository layer ***");
        try {
        	Integer entityId = (Integer)emf.getPersistenceUnitUtil().getIdentifier(baseTable);
        	SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	Integer currentUserId = securityUser.getUserId();
        	
        	Timestamp currentTime = new Timestamp(new Date().getTime());
        	
        	
        	if (entityId == null || entityId == -1) {
        		// it's a new field
        		baseTable.setCreatedBy(currentUserId);
        		baseTable.setCreationDate(currentTime);
        	} 
        	baseTable.setLastUpdatedBy(currentUserId);
        	baseTable.setLastUpdateDate(currentTime);
        } catch (Exception e) {
        	LOG.debug("Couldn't autosave basic fields", e);
        }
        
    }

   
}
