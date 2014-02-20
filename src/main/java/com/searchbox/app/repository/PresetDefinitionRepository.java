package com.searchbox.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.app.domain.PresetDefinition;
import com.searchbox.app.domain.Searchbox;

public interface PresetDefinitionRepository extends CrudRepository<PresetDefinition, Long> {

	public PresetDefinition findPresetDefinitionBySearchboxAndSlug(Searchbox searchbox, String slug);

	public List<PresetDefinition> findAllBySearchbox(Searchbox searchbox);
	
}
