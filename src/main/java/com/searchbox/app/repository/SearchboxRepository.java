package com.searchbox.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.app.domain.Searchbox;

public interface SearchboxRepository extends CrudRepository<Searchbox, Long> {

	public Searchbox findBySlug(String slug);
}
