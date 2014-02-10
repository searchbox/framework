package com.searchbox.core.search.debug;

import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementType;

public class SearchError extends SearchElement {

	Exception e;
	
	public SearchError(String errorMessage, Exception e) {
		super(errorMessage);
		this.setType(SearchElementType.DEBUG);
	}
}
