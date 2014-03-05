/*******************************************************************************
 * Copyright Searchbox - http://www.searchbox.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.searchbox.core.search;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.BooleanClause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchCondition;

@SearchCondition(urlParam="")
public abstract class AbstractSearchCondition {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSearchCondition.class);
	
	private Float boost = 1f;
	
	private BooleanClause clause;
		
	private List<AbstractSearchCondition> innerConditions = new ArrayList<AbstractSearchCondition>();
	
	public List<AbstractSearchCondition> getInnerConditions() {
		return innerConditions;
	}

	public void setInnerConditions(List<AbstractSearchCondition> innerConditions) {
		this.innerConditions = innerConditions;
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
	
	public AbstractSearchCondition addInnerCondition(AbstractSearchCondition condition){
		this.innerConditions.add(condition);
		return this;
	}
	
	public Boolean hasSubConditions(){
		return this.innerConditions.size() > 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boost == null) ? 0 : boost.hashCode());
		result = prime * result + ((clause == null) ? 0 : clause.hashCode());
		result = prime * result
				+ ((innerConditions == null) ? 0 : innerConditions.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
				LOGGER.error("Could not compare Objects",e);
				return false;
			}
		}
		return true;
	}
	
	public String getUrlParam(){
		return this.getClass().getAnnotation(SearchCondition.class).urlParam();
	}
}
