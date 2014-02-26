package com.searchbox.framework.repository;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.framework.domain.SearchElementDefinition;

public interface SearchElementRepository extends
		CrudRepository<SearchElementDefinition, Long> {

}
