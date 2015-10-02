package com.bergermobile.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.domain.UserRole;

public interface UserRepository extends CrudRepository<User, Integer> {

	public User findByName(String name);

	public User findByUserTypeAndUsername(Short userType, String username);
	
	public User findByLoginTypeAndUsername(Short type, String usernameg);

	public User findByUserId(Integer userId);
	
	//@Query("delete u.userRoles from User u where u.userRoles in :userRoles")
	//public void deleteUserRoles(@Param("userRoles") List<UserRole> userRoles);
	
}
