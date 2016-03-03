package com.bergermobile.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bergermobile.persistence.domain.Application;

public interface ApplicationRepository extends CrudRepository<Application, Integer> {

	public Application findByApplicationId(Integer applicationId);

	public Application findByApplicationName(String applicationName);
	
	@Query("select distinct application from Application as application inner join application.roles as applicationRoles inner join applicationRoles.userRoles as userRoles inner join userRoles.user as user where user.userId = :userId")
	public List<Application> findByUserId(@Param("userId") Integer userId);
}
