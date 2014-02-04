package com.searchbox.domain.search.stat;

import com.searchbox.domain.search.SearchElement;
import com.searchbox.domain.search.SearchElementType;

public class BasicSearchStats extends SearchElement {

	public BasicSearchStats(String label) {
		super(label);
		this.setType(SearchElementType.STAT);
	}

	public BasicSearchStats(String label, Integer position) {
		super(label, position);
		this.setType(SearchElementType.STAT);
	}

}
