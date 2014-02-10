package com.searchbox.core.search.stat;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementType;

@SearchComponent
public class BasicSearchStats extends SearchElement {

	private Long hitCount;
	private Long searchTime;
	
	public long getHitCount() {
		return hitCount;
	}

	public void setHitCount(long hitCount) {
		this.hitCount = hitCount;
	}

	public long getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(long searchTime) {
		this.searchTime = searchTime;
	}
	
	public BasicSearchStats(){
		super();
		this.setType(SearchElementType.STAT);
	}

	public BasicSearchStats(String label) {
		super(label);
		this.setType(SearchElementType.STAT);
	}

	public BasicSearchStats(String label, Integer position) {
		super(label, position);
		this.setType(SearchElementType.STAT);
	}

}