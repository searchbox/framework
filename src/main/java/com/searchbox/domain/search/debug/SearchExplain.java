package com.searchbox.domain.search.debug;

import com.searchbox.domain.search.SearchElement;
import com.searchbox.domain.search.SearchElementType;


public class SearchExplain extends SearchElement {

	protected SearchExplain(String label) {
		super(label);
		this.setType(SearchElementType.DEBUG);
	}
}
