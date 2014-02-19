package com.searchbox.ref;

import java.io.Serializable;

public enum Order implements Serializable {
	
	BY_KEY("key","order by key"), 
	BY_VALUE("value","order by value");
	
	private String name;
	private String description;

    private Order(String name, String description) {
        this.name = name;
        this.description = description;
    }

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}