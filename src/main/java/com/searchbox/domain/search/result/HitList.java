package com.searchbox.domain.search.result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.searchbox.domain.search.SearchElementType;
import com.searchbox.domain.search.SearchElementWithValues;
import com.searchbox.domain.search.ValueElement;



public class HitList extends SearchElementWithValues<HitList.Hit> {
	
	private List<String> fields;
	
	public HitList(){
		super();
		this.type = SearchElementType.VIEW;
	}
	
	public static class Hit extends ValueElement<Map<String, String>> implements Comparable<Hit> {

		private Float score;
		
		public Hit(){
			super("");
			this.value = new HashMap<String, String>();
		}
		
		public Float getScore(){
			return this.score;
		}

		@Override
		public int compareTo(Hit hit) {
			return score.compareTo(hit.getScore()) * -1;
		}

		public void setScore(Float score) {
			this.score = score;
		}
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public void addHit(Hit hit) {
		this.values.add(hit);
	}
}
