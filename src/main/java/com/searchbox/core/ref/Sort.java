package com.searchbox.core.ref;

public enum Sort {

	DESC("desc","descending order"), 
	ASC("asc","ascending order");
	
	private String name;
	private String description;

    private Sort(String name, String description) {
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