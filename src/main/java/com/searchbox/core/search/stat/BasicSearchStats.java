package com.searchbox.core.search.stat;

import org.apache.solr.client.solrj.response.QueryResponse;

import com.searchbox.anno.SearchAdapter;
import com.searchbox.anno.SearchAdapterMethod;
import com.searchbox.anno.SearchAdapterMethod.Target;
import com.searchbox.anno.SearchAttribute;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.search.SearchElement;

@SearchComponent
public class BasicSearchStats extends SearchElement {

	private Long hitCount = 0l;
	private Long searchTime = 0l;
	
	@SearchAttribute
	private Boolean viewTime = true;
	
	@SearchAttribute
	private Boolean viewCount = true;
	
	public Boolean getViewTime() {
		return viewTime;
	}

	public void setViewTime(Boolean viewTime) {
		this.viewTime = viewTime;
	}

	public Boolean getViewCount() {
		return viewCount;
	}

	public void setViewCount(Boolean viewCount) {
		this.viewCount = viewCount;
	}

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
		super(null,SearchElement.Type.STAT);
	}

	public BasicSearchStats(String label) {
		super(label,SearchElement.Type.STAT);
	}
}

@SearchAdapter(target=BasicSearchStats.class)
class BasicSearchStatsAdapter {

	@SearchAdapterMethod(target=Target.POST)
	public BasicSearchStats doAdapt(BasicSearchStats searchElement,
			QueryResponse response) {
		if(response.getResults() != null){
			searchElement.setHitCount(response.getResults().getNumFound());
		}
		searchElement.setSearchTime(response.getElapsedTime());
		return searchElement;
	}
}