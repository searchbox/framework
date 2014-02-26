package com.searchbox.framework.repository;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.framework.domain.SearchEngineDefinition;


public interface SearchEngineRepository extends CrudRepository<SearchEngineDefinition, Long> {

}
