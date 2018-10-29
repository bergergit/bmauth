package com.bergermobile.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bergermobile.persistence.domain.Application;
import com.bergermobile.persistence.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

	public Role findByRoleId(Integer roleId);
	
	public Role findByRoleName(String roleName);

	public List<Role> findByApplication(Application application);
	
	public Role findByRoleNameAndApplication(String roleName, Application application);
	
}
