package com.searchbox.framework.repository;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.framework.domain.CollectionDefinition;

public interface CollectionRepository extends CrudRepository<CollectionDefinition, Long> {

}
