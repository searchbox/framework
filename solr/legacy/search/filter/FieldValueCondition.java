package com.searchbox.core.search.filter;

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.SearchCondition;
import com.searchbox.core.SearchConverter;
import com.searchbox.core.search.AbstractSearchCondition;

@SearchCondition(urlParam = "ff")
public class FieldValueCondition extends AbstractSearchCondition {

  public static final String FIELD_NAME_ATTR = "fieldName";
  public static final String VALUE_ATTR = "value";
  public static final String TAGED_ATTR = "taged";

  @SearchAttribute
  String fieldName;

  @SearchAttribute
  String value;

  @SearchAttribute
  Boolean taged;

  public FieldValueCondition() {

  }

  public FieldValueCondition(String field, String value) {
    this.fieldName = field;
    this.value = value;
    this.taged = false;
  }

  public FieldValueCondition(String field, String value, Boolean taged) {
    this.fieldName = field;
    this.value = value;
    this.taged = taged;
  }

  public String getFieldName() {
    return fieldName;
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

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  @Override
  public String getParamValue() {
    return getFieldName() + "[" + getValue() + "]" + ((getTaged()) ? "x" : "");
  }

  /** Format of FieldValueFacet is key[value]s where s makes it stick */
  @SearchConverter
  public static class FieldValueConditionConverter implements
      Converter<String, FieldValueCondition> {
    @Override
    public FieldValueCondition convert(String source) {
      String cfield = source.split("\\[")[0];
      String cvalue = source.split("\\[")[1].split("]")[0];
      // FIXME Problem here, the facet will not be sticky if not forced...
      // :/
      return new FieldValueCondition(cfield, cvalue, true);
    }
  }
}
