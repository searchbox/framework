package com.searchbox.core.search;

import java.net.URL;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.SearchConverter;
import com.searchbox.core.search.sort.FieldSort.Condition;

@SearchComponent(urlParam="xxx")
public abstract class SearchElement implements Comparable<SearchElement>{
	
	private static Logger logger = LoggerFactory.getLogger(SearchElement.class);
	
	public final static String URL_PARAM = "xoxo";
	
	public enum Type {
		QUERY, FACET, FILTER, VIEW, ANALYTIC, SORT, STAT, DEBUG, UNKNOWN
	}
	
	@SearchAttribute
	private String label;
	
	private Long definitionId;
	
	private Integer position;
	
	protected Type type = Type.FILTER;
	
	public SearchElement(){}
	
	protected SearchElement(String label, SearchElement.Type type){
		this.label = label;
		this.type = type;
	}
	
	public String getUrlParam(){
		return this.getClass().getAnnotation(SearchComponent.class).urlParam();
	}
	
	/* (non-Javadoc)
	 * @see com.searchbox.core.search.SearchElement#getLabel()
	 */
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/* (non-Javadoc)
	 * @see com.searchbox.core.search.SearchElement#getPosition()
	 */
	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	/* (non-Javadoc)
	 * @see com.searchbox.core.search.SearchElement#getType()
	 */
	public SearchElement.Type getElementType() {
		return type;
	}

	public void setType(SearchElement.Type type) {
		this.type = type;
	}

	@Override
	public int compareTo(SearchElement searchElement) {
		return this.getPosition().compareTo(searchElement.getPosition());
		
	}
	
	public URL getView(){
		//TODO partial should be next to the .class file... 
		System.out.println("XOXOXOXOXOX: " + this.getClass());
		System.out.println("XOXOXOXOXOX: " + this.getClass().getResource("view.jspx"));
		return this.getClass().getResource("view.jspx");
	}
	
	@Override
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public Long getDefinitionId() {
		return definitionId;
	}
	
	public void setDefinitionId(Long definitionId) {
		this.definitionId = definitionId;
	}
	
	@SearchConverter
	public static class Converter implements
	org.springframework.core.convert.converter.Converter<String, SearchCondition> {

		@Override
		public Condition convert(String source) {
			logger.error("A searchElemenet is missing its URL_PARAM!!!");
			logger.error("please make sure to define a \"public static final String URL_PARAM = ...\" ");
			logger.error("within your SearchElement class. That URL_PARAM is used on forms (jspx) as");
			logger.error("as to identify the proper converter to your class. IE:");
			logger.error("@SearchConverter(urlParam=MyOwnSearchElement.URL_PARAM)");
			logger.error("public static class Converter implements org.spring...Converter<String, MyOwnSearchElement> {");
			return null;
		}
	}
}
