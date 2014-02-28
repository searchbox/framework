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
package com.searchbox.core.search.paging;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.searchbox.core.PostSearchAdapter;
import com.searchbox.core.PreSearchAdapter;
import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.SearchConverter;
import com.searchbox.core.search.ConditionalValueElement;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementWithConditionalValues;
import com.searchbox.core.search.ValueElement;
import com.searchbox.core.search.paging.BasicPagination.PageCondition;

@SearchComponent(urlParam="p")
public class BasicPagination extends SearchElementWithConditionalValues<BasicPagination.Page, BasicPagination.PageCondition>{
		
	private Integer hitsPerPage;
	private Integer currentPage = 1;
	
	private Long numberOfHits;
	
	public BasicPagination(){
		super("Basic Pagination",SearchElement.Type.VIEW);
	}

	public Integer getHitsPerPage() {
		return hitsPerPage;
	}

	public void setHitsPerPage(Integer hitsPerPage) {
		this.hitsPerPage = hitsPerPage;
	}

	public Long getNumberOfHits() {
		return numberOfHits;
	}

	public void setNumberOfHits(long l) {
		this.numberOfHits = l;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	@Override
	public void mergeSearchCondition(SearchCondition condition) {
		if(PageCondition.class.equals(condition.getClass())){
			PageCondition pcondition = (PageCondition)condition;
			for(Page page:this.getValues()){
				if(page.start.equals(pcondition.start)){
					page.selected = true;
					this.currentPage = (page.start / this.hitsPerPage) + 1;
				}
			}
		}
	}

	public class Page extends ConditionalValueElement<PageCondition>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4978333478047395794L;

		Integer start;
		
		boolean selected;
		
		public Page() {
			super("Page attr");
		}
		
		public Page(Integer start) {
			super("Page attr");
			this.start = start;
		}

		public Integer getPage() {
			return (int) Math.ceil(start/(double)hitsPerPage)+1;
		}
		
		public Integer getStart(){
			return start;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		@Override
		public String geParamValue() {
			return Integer.toString(start);
		}

		@Override
		public PageCondition getSearchCondition() {
			return new BasicPagination.PageCondition(start);
		}

		@Override
		public int compareTo(ValueElement other) {
			Page opage = (Page)other;
			return this.start.compareTo(opage.start);
		}
		
	}
	
	public static class PageCondition extends SearchCondition {
		Integer start;
		public PageCondition(Integer start) {
			this.start = start;
		}
	}

	@SearchConverter	
	public static class Converter implements 
		org.springframework.core.convert.converter.Converter<String, BasicPagination.PageCondition> {

		@Override
		public PageCondition convert(String source) {
			return new PageCondition(Integer.parseInt(source));
		}

	}
}

@SearchAdapter
class BasicPaginationAdaptor  {

	
	@PostSearchAdapter
	public BasicPagination getPaginationSettings(BasicPagination searchElement,
			SolrQuery query, QueryResponse response) {
		
		Integer hitsPerPage = query.getRows();
		Long numberOfHits = response.getResults().getNumFound();

		//If rows in not set, we must gues sit form resultset
		if(hitsPerPage == null){
			hitsPerPage = response.getResults().size();
		}
		
		//TODO... dont make all pages.
		for(int p=0; p*hitsPerPage<numberOfHits; p++){
			searchElement.addValueElement(searchElement.new Page(p*hitsPerPage));
		}
		
		searchElement.setNumberOfHits(numberOfHits);
		searchElement.setHitsPerPage(hitsPerPage);
		return searchElement;
	}

	@PreSearchAdapter
	public SolrQuery setPagination(PageCondition condition,
			SolrQuery query) {
		query.setStart(condition.start);
		return query;
	}
}
