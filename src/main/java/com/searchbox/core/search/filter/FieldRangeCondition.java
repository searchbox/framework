package com.searchbox.core.search.filter;

import org.springframework.core.convert.converter.Converter;

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.SearchCondition;
import com.searchbox.core.SearchConverter;
import com.searchbox.core.search.AbstractSearchCondition;

@SearchCondition(urlParam = "fr")
public class FieldRangeCondition extends AbstractSearchCondition {


  public static final String FIELD_NAME_ATTR = "fieldName";
  public static final String MIN_ATTR = "min";
  public static final String MAX_ATTR = "max";
  public static final String TAGED_ATTR = "taged";

  @SearchAttribute
  String fieldName;

  @SearchAttribute
  String min;

  @SearchAttribute
  String max;

  @SearchAttribute
  Boolean taged;

  public FieldRangeCondition(){

  }

  public FieldRangeCondition(String fieldName, String min, String max){
    this.fieldName = fieldName;
    this.min = min;
    this.max = max;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getMin() {
    return min;
  }

  public void setMin(String min) {
    this.min = min;
  }

  public String getMax() {
    return max;
  }

  public void setMax(String max) {
    this.max = max;
  }

  public Boolean getTaged() {
    return taged;
  }

  public void setTaged(Boolean taged) {
    this.taged = taged;
  }

  @Override
  public String getParamValue() {
    return getFieldName()+"["+min+" TO "+max+"]";
  }

  /** Format of FieldValueFacet is key[value]s where s makes it stick */
  @SearchConverter
  public static class FieldRangeConditionConverter implements
      Converter<String, FieldRangeCondition> {
    @Override
    public FieldRangeCondition convert(String source) {
      String cfield = source.split("\\[")[0];
      String cvalue = source.split("\\[")[1].split("]")[0];

      // FIXME Problem here, the facet will not be sticky if not forced...
      // :/
      return new FieldRangeCondition(); //cfield, cvalue, true
    }
  }
}
