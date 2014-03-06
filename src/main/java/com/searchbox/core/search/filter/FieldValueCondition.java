package com.searchbox.core.search.filter;

import org.springframework.core.convert.converter.Converter;

import com.searchbox.core.SearchCondition;
import com.searchbox.core.SearchConverter;
import com.searchbox.core.dm.Field;
import com.searchbox.core.ref.StringUtils;
import com.searchbox.core.search.AbstractSearchCondition;

@SearchCondition(urlParam="ff")
public class FieldValueCondition extends AbstractSearchCondition {

	Field field;
	String value;
	Boolean taged;
	
	public FieldValueCondition(Field field, String value) {
		this.field = field;
		this.value = value;
		this.taged = false;
	}
	
	public FieldValueCondition(Field field, String value, Boolean taged) {
		this.field = field;
		this.value = value;
		this.taged = taged;
	}

	public String getFieldName() {
		return field.getKey();
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Field getField() {
		return this.field;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Boolean getTaged() {
		return taged;
	}

	public void setTaged(Boolean taged) {
		this.taged = taged;
	}

	/** Format of FieldValueFacet is key[value]d * where d is a class shortcut */
	@SearchConverter
	public static class FieldValueConditionConverter
			implements Converter<String, FieldValueCondition> {
		@Override
		public FieldValueCondition convert(String source) {
			String field = source.split("\\[")[0];
			String value = source.split("\\[")[1].split("]")[0];
			String slug = source.split("\\[")[1].split("]")[1];
			Class<?> clazz = StringUtils.SlugToClass(slug);
			return new FieldValueCondition(new Field(clazz,field), value, true);
		}
	}
}
