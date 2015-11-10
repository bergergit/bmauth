package com.bergermobile.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bergermobile.persistence.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	public User findByName(String name);

	public User findByUserTypeAndUsername(Short userType, String username);

	public User findByLoginTypeAndUsername(Short type, String usernameg);

	public User findByUserId(Integer userId);

	public User findByEmail(String email);

    @Query("select user from User as user inner join user.userRoles as userRoles inner join userRoles.role as role inner join role.application as application where user.email = :email and application.applicationId = :applicationId")	
	public User findByEmailAndApplicationId(@Param("email") String email, @Param("applicationId") Integer applicationId);

}
