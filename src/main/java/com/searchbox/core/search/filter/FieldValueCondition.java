package com.searchbox.core.search.filter;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((taged == null) ? 0 : taged.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof FieldValueCondition))
			return false;
		FieldValueCondition other = (FieldValueCondition) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (taged == null) {
			if (other.taged != null)
				return false;
		} else if (!taged.equals(other.taged))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
			//TODO Problem here, the facet will not be sticky if not forced... :/
			return new FieldValueCondition(new Field(clazz,field), value);
		}
	}
}
