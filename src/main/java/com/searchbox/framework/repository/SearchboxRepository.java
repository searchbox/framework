package com.searchbox.framework.repository;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.framework.domain.Searchbox;

public interface SearchboxRepository extends CrudRepository<Searchbox, Long> {

	public Searchbox findBySlug(String slug);
}
