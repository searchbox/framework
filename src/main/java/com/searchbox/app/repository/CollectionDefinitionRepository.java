package com.searchbox.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.app.domain.CollectionDefinition;

public interface CollectionDefinitionRepository extends CrudRepository<CollectionDefinition, Long> {

}
