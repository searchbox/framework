package com.searchbox.framework.repository;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.framework.domain.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
}
