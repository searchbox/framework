package com.searchbox.app.domain;

public interface ElementFactory<K extends Object> {

	public K getInstance();
}
