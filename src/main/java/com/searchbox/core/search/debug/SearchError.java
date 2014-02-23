package com.searchbox.core.search.debug;

import com.searchbox.core.search.SearchElement;

public class SearchError extends SearchElement {

	Exception e;
	
	public SearchError(String errorMessage, Exception e) {
		super(errorMessage, SearchElement.Type.DEBUG);
	}
}
