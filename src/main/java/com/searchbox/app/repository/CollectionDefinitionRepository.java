package com.searchbox.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.app.domain.CollectionDefinition;
import com.searchbox.app.domain.PresetDefinition;
import com.searchbox.app.domain.Searchbox;

public interface CollectionDefinitionRepository extends CrudRepository<CollectionDefinition, Long> {

}
