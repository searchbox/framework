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

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.SearchCondition;
import com.searchbox.core.SearchConverter;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.core.search.ConditionalSearchElement;
import com.searchbox.core.search.SearchElement;

@SearchComponent
public class BasicPagination extends ConditionalSearchElement<BasicPagination.PageCondition>{
		
	@SearchAttribute
	private Integer hitsPerPage = 10;
	
	private Integer currentPage = 0;
	private Integer maxPage;
	
	private Long numberOfHits;
	
	public BasicPagination(){
		super();
		this.type = SearchElement.Type.VIEW;
	}

	
	public Integer getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(Integer maxPage) {
		this.maxPage = maxPage;
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
	public void mergeSearchCondition(AbstractSearchCondition condition) {
		if(PageCondition.class.equals(condition.getClass())){
			PageCondition pcondition = (PageCondition)condition;
				this.currentPage = pcondition.getPage();
		}
	}

	
	@SearchCondition(urlParam="p")
	public static class PageCondition extends AbstractSearchCondition {
		Integer page;
		public PageCondition(Integer page) {
			this.page = page;
		}
		
		public Integer getPage(){
			return this.page;
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

	@Override
	public String geParamValue() {
		return this.currentPage+"";
	}

	@Override
	public PageCondition getSearchCondition() {
		return new PageCondition(this.currentPage);
	}

	@Override
	public Class<?> getConditionClass() {
		return PageCondition.class;
	}
}
