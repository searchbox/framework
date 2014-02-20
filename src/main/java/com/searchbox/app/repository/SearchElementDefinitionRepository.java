package com.searchbox.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.app.domain.SearchElementDefinition;

public interface SearchElementDefinitionRepository extends
		CrudRepository<SearchElementDefinition, Long> {

}
