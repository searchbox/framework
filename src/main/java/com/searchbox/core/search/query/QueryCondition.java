package com.searchbox.core.search.query;

import com.searchbox.core.SearchCondition;
import com.searchbox.core.SearchConverter;
import com.searchbox.core.search.AbstractSearchCondition;

@SearchCondition(urlParam = "q")
public class QueryCondition extends AbstractSearchCondition {

  String query;

  QueryCondition(String query) {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }

  @Override
  public String getParamValue() {
    return query;
  }

  @SearchConverter
  public static class Converter
      implements
      org.springframework.core.convert.converter.Converter<String, QueryCondition> {

    @Override
    public QueryCondition convert(String source) {
      return new QueryCondition(source);
    }
  }
}