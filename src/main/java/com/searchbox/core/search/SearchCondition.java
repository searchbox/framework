package com.searchbox.core.search;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;

public abstract class SearchCondition {
	
	private Float boost = 1f;
	
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
		//TODO check that Q is not null, and if it is throws an exception
		if(q != null){
			q.setBoost(this.getBoost());
		}
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
	
	@Override
	public boolean equals(Object other){
		if(!this.getClass().equals(other.getClass())){
			return false;
		} else {
			try {
				for(PropertyDescriptor propertyDescriptor : 
				    Introspector.getBeanInfo(this.getClass()).getPropertyDescriptors()){
				   
					Method method = propertyDescriptor.getReadMethod();
					Object res1 = method.invoke(this);
					Object res2 = method.invoke(other);
					
					if((res1 == null && res2 != null) || (res1 != null && res2 == null)){
						return false;
					}
					if(res1!= null && res2 != null && !res1.equals(res2)){
						return false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
