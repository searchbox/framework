package com.searchbox.framework.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.domain.Searchbox;

public interface PresetRepository extends CrudRepository<PresetDefinition, Long> {

	public PresetDefinition findPresetDefinitionBySearchboxAndSlug(Searchbox searchbox, String slug);

	public List<PresetDefinition> findAllBySearchbox(Searchbox searchbox);
	
}
