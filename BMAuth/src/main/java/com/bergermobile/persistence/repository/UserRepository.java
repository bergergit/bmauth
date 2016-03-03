package com.bergermobile.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bergermobile.persistence.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	public User findByName(String name);

	public User findByUserTypeAndUsername(Short userType, String username);

	public User findByLoginTypeAndUsername(Short type, String username);

	public User findByUserId(Integer userId);

	public User findByEmail(String email);
	
	/**
	 * This will only retrieve usernames that belong to that type
	 * @param email
	 * @param applicationId
	 * @return
	 */
	@Query("select user from User user left join user.userRoles as userRoles where user.username = :username and user.loginType = :loginType and userRoles.role.application.realm = :realm")	
	public User findByLoginTypeAndUsernameAndRealm(@Param("loginType") Short loginType, @Param("username") String username, @Param("realm") String realm);

    @Query("select user from User as user inner join user.userRoles as userRoles inner join userRoles.role as role inner join role.application as application where user.email = :email and application.applicationId = :applicationId")	
	public User findByEmailAndApplicationId(@Param("email") String email, @Param("applicationId") Integer applicationId);
    
    @Query("select distinct user from User as user inner join user.userRoles as userRoles inner join userRoles.role as role inner join role.application as application where application.applicationName = :applicationName")	
	public List<User> findByApplicationName(@Param("applicationName") String applicationName);

    @Query("select user from User as user inner join user.userRoles as userRoles inner join userRoles.role as role inner join role.application as application where user.userId = :userId and application.applicationId = :applicationId")	
	public User findByUserIdAndApplicationId(@Param("userId") Integer userId, @Param("applicationId") Integer applicationId);
    
    @Query("select u from User u where name like %:search% or email like %:search%")
	public Page<User> findAllWithCriterias(@Param("search") String search, Pageable page);

}
