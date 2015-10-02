package com.bergermobile.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bergermobile.persistence.domain.Application;
import com.bergermobile.persistence.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

	public Role findByRoleId(Integer roleId);

	public List<Role> findByApplication(Application application);
	
	// delete orphan roles, so that we can save them again
	//@Modifying
	//@Query("delete from UserRole ur where ur.role.application != null and ur.user in (select u from User u where user = :user)")
	//public void deleteOrphanUserRoles(@Param("user") User user);
	
}
