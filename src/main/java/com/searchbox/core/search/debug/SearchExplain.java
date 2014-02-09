package com.searchbox.core.search.debug;

import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementType;


public class SearchExplain extends SearchElement {

	protected SearchExplain(String label) {
		super(label);
		this.setType(SearchElementType.DEBUG);
	}
}
