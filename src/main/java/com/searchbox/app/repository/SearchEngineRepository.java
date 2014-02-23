package com.searchbox.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.app.domain.PresetDefinition;
import com.searchbox.app.domain.SearchEngineDefinition;


public interface SearchEngineRepository extends CrudRepository<SearchEngineDefinition, Long> {

}
