package com.searchbox.app.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.app.domain.FieldAttributeDefinition;
import com.searchbox.app.domain.PresetDefinition;

public interface FieldAttributeRepository extends CrudRepository<FieldAttributeDefinition, Long> {

	public Set<FieldAttributeDefinition> findAllByPreset(PresetDefinition p);
}
