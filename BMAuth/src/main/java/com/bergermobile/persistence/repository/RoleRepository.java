package com.bergermobile.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.bergermobile.persistence.domain.Role;

public interface RoleRepository  extends CrudRepository<Role, Integer> {

	
	
}
