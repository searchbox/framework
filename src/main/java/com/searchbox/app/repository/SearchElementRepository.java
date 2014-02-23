package com.searchbox.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.app.domain.SearchElementDefinition;

public interface SearchElementRepository extends
		CrudRepository<SearchElementDefinition, Long> {

}
