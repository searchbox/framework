package com.searchbox.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.app.domain.SearchEngineDefinition;


public interface SearchEngineDefinitionRepository extends CrudRepository<SearchEngineDefinition, Long> {

}
