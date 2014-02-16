package com.searchbox.core.search.paging;

import com.searchbox.anno.SearchComponent;
import com.searchbox.core.search.ConditionalValueElement;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElementType;
import com.searchbox.core.search.SearchElementWithConditionalValues;
import com.searchbox.core.search.ValueElement;

@SearchComponent(prefix = "p", condition = BasicPagination.PageCondition.class, converter = BasicPagination.Converter.class)
public class BasicPagination extends SearchElementWithConditionalValues<BasicPagination.Page, BasicPagination.PageCondition>{

	BasicPagination(){
		super("Basic Pagination");
		this.setType(SearchElementType.VIEW);
	}

	@Override
	public void mergeSearchCondition(SearchCondition condition) {
		if(PageCondition.class.equals(condition.getClass())){
			PageCondition pcondition = (PageCondition)condition;
			for(Page page:this.getValues()){
				if(page.page == pcondition.page){
					page.selected = true;
				}
			}
		}
	}

	public static class Page extends ConditionalValueElement<PageCondition>{

		Integer page;
		boolean selected;
		public Page() {
			super("Page attr");
		}

		@Override
		public String geParamValue() {
			return page.toString();
		}

		@Override
		public PageCondition getSearchCondition() {
			return new BasicPagination.PageCondition(page);
		}

		@Override
		public int compareTo(ValueElement other) {
			Page opage = (Page)other;
			return this.page.compareTo(opage.page);
		}
		
	}
	
	public static class PageCondition extends SearchCondition {
		Integer page;
		public PageCondition(Integer page) {
			this.page = page;
		}
	}

	public static class Converter {

	}
}
