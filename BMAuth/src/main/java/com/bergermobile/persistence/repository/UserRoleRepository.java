package com.bergermobile.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.domain.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
	//@Modifying
	//@Query("delete from UserRole ur where ur in (:userRoles)")
	//public void deleteUserRoles(@Param("userRoles") List<UserRole> userRoles);
	
	public List<UserRole> findByUser(User user);
}
