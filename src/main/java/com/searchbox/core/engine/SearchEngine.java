package com.searchbox.core.engine;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import com.searchbox.domain.Collection;

public abstract class SearchEngine<K extends SearchQuery> {

	@OneToMany(mappedBy = "engine", cascade = CascadeType.ALL)
	private List<Collection> collections;

}
