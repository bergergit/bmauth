package com.bergermobile.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bergermobile.persistence.domain.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
	public List<UserRole> findByUserUserIdAndRoleApplicationIsNotNull(Integer userId);
}
