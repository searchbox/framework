package com.searchbox.core.search.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.searchbox.core.search.SearchElementType;
import com.searchbox.core.search.SearchElementWithValues;
import com.searchbox.core.search.ValueElement;



public class HitList extends SearchElementWithValues<HitList.Hit> {
	
	private List<String> fields;
	
	public HitList(){
		super();
		this.type = SearchElementType.VIEW;
		this.fields = new ArrayList<String>();
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

	public List<String> getFields() {
		return this.fields;
	}

	public void addHit(Hit hit) {
		this.values.add(hit);
	}
}
