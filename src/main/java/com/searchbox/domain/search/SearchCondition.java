package com.searchbox.domain.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;

public abstract class SearchCondition {
	
	private Float boost;
	
	private BooleanClause clause;
	
	private List<SearchCondition> innerConditions = new ArrayList<SearchCondition>();
	
	protected abstract Query getConditionalQuery();
			
	public Query getQuery(){
		/** for later
		BooleanQuery q = new BooleanQuery();
		for(SearchCondition ic:this.innerConditions){
			q.add(ic.getQuery(),ic.getClause().getOccur());
		}
		*/
		Query q = this.getConditionalQuery();
		q.setBoost(this.getBoost());
		return q;
	}

	public Float getBoost() {
		return boost;
	}

	public void setBoost(Float boost) {
		this.boost = boost;
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
