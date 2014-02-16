package com.searchbox.core.search.paging;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.CommonParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.adaptor.SolrConditionAdapter;
import com.searchbox.core.adaptor.SolrElementAdapter;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.ConditionalValueElement;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElementType;
import com.searchbox.core.search.SearchElementWithConditionalValues;
import com.searchbox.core.search.ValueElement;
import com.searchbox.core.search.paging.BasicPagination.Page;
import com.searchbox.core.search.paging.BasicPagination.PageCondition;

@SearchComponent(prefix = "p", condition = BasicPagination.PageCondition.class, converter = BasicPagination.Converter.class)
public class BasicPagination extends SearchElementWithConditionalValues<BasicPagination.Page, BasicPagination.PageCondition>{
	
	Integer hitsPerPage;
	Long numberOfHits;
	
	BasicPagination(){
		super("Basic Pagination");
		this.setType(SearchElementType.VIEW);
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

	@Override
	public void mergeSearchCondition(SearchCondition condition) {
		if(PageCondition.class.equals(condition.getClass())){
			PageCondition pcondition = (PageCondition)condition;
			for(Page page:this.getValues()){
				if((page.start) == pcondition.start){
					page.selected = true;
				}
			}
		}
	}

	public class Page extends ConditionalValueElement<PageCondition>{

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

	public static class Converter implements 
		org.springframework.core.convert.converter.Converter<String, BasicPagination.PageCondition> {

		@Override
		public PageCondition convert(String source) {
			return new PageCondition(Integer.parseInt(source));
		}

	}
}

@SearchAdaptor
class BasicPaginationAdaptor implements SolrConditionAdapter<BasicPagination.PageCondition>,
	SolrElementAdapter<BasicPagination> {

	private static Logger logger = LoggerFactory.getLogger(BasicPaginationAdaptor.class);

	@Override
	public SolrQuery doAdapt(Preset preset, BasicPagination searchElement,
			SolrQuery query) {
		return query;
	}

	@Override
	public BasicPagination doAdapt(Preset preset,
			BasicPagination searchElement, SolrQuery query,
			QueryResponse response) {
		
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

	@Override
	public SolrQuery doAdapt(Preset preset, PageCondition condition,
			SolrQuery query) {
		query.setStart(condition.start);
		return query;
	}
}
