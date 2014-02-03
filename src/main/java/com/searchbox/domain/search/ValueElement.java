package com.searchbox.domain.search;


public class ValueElement<K extends Comparable<K>> implements Comparable<ValueElement<K>>{
	
	enum Order {
		KEY, VALUE
	}
	
	enum Sort {
		DESC, ASC
	}
	
	protected String label;
	
	protected K value;
		
	private Order order = Order.KEY;
	private Sort sort = Sort.DESC;


	public ValueElement(String label) {
		this.label = label;
	}
	
	public ValueElement(String label, K value){
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public K getValue() {
		return value;
	}

	public void setValue(K value) {
		this.value = value;
	}

	@Override
	public int compareTo(ValueElement<K> valueElement) {
		int diff;
		if(order.equals(Order.KEY)){
			diff = this.getLabel().compareTo(valueElement.getLabel());
		} else {
			diff = this.getValue().compareTo(valueElement.getValue());
		}
		//TODO check if -1 is DESC or ASC.
		return diff*((sort.equals(Sort.DESC))?1:-1);
	}
}
