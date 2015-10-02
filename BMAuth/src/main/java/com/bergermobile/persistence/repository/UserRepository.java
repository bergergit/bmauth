package com.bergermobile.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.bergermobile.persistence.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	public User findByName(String name);

	public User findByUserTypeAndUsername(Short userType, String username);
	
	public User findByLoginTypeAndUsername(Short type, String usernameg);

	public User findByUserId(Integer userId);
}
