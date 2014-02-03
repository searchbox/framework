package com.searchbox.domain.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;

public abstract class SearchCondition {
	
	private Float weight;
	
	private BooleanClause clause;
	
	private List<SearchCondition> innerConditions = new ArrayList<SearchCondition>();
	
	abstract Query getQuery();

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public BooleanClause getClause() {
		return clause;
	}

	public void setClause(BooleanClause clause) {
		this.clause = clause;
	}
	
	public SearchCondition addInnerCondition(SearchCondition condition){
		this.innerConditions.add(condition);
		return this;
	}
	
	public Boolean hasSubConditions(){
		return this.innerConditions.size() > 0;
	}
}
