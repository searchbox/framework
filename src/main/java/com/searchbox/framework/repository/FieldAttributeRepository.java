package com.searchbox.framework.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.framework.domain.FieldAttributeDefinition;
import com.searchbox.framework.domain.PresetDefinition;

public interface FieldAttributeRepository extends CrudRepository<FieldAttributeDefinition, Long> {

	public Set<FieldAttributeDefinition> findAllByPreset(PresetDefinition p);
}
