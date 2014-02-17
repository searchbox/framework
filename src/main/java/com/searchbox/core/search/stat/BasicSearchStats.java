package com.searchbox.core.search.stat;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.anno.SearchAttribute;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.adaptor.SolrElementAdapter;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementType;

@SearchComponent
public class BasicSearchStats extends SearchElement {

	private Long hitCount;
	private Long searchTime;
	
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
		super();
		this.setType(SearchElementType.STAT);
	}

	public BasicSearchStats(String label) {
		super(label);
		this.setType(SearchElementType.STAT);
	}
}

@SearchAdaptor
class BasicSearchStatsAdapter implements SolrElementAdapter<BasicSearchStats>{

	@Override
	public SolrQuery doAdapt(Preset preset,
			BasicSearchStats searchElement, SolrQuery query) {
		return query;
	}

	@Override
	public BasicSearchStats doAdapt(Preset preset,
			BasicSearchStats searchElement, SolrQuery query,
			QueryResponse response) {
		if(response.getResults() != null){
			searchElement.setHitCount(response.getResults().getNumFound());
		}
		searchElement.setSearchTime(response.getElapsedTime());
		return searchElement;
	}
}