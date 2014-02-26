package com.searchbox.framework.domain;

public interface ElementFactory<K extends Object> {

	public K getInstance();
}
