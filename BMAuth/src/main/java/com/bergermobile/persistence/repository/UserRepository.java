package com.bergermobile.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bergermobile.persistence.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	public List<User> findByName(String name);

	public User findByUserTypeAndUsername(Short i, String string);
	
	public User findByUserId(Integer userId);
	
}
