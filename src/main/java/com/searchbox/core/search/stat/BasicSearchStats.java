package com.searchbox.core.search.stat;

import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementType;

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
