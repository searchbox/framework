package com.searchbox.core.search.paging;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.query.Query;
import com.searchbox.core.response.Response;
import com.searchbox.core.search.paging.BasicPagination.PageCondition;

@SearchAdapter
public class BasicPaginationAdaptor {

  @SearchAdapterMethod(execute = Time.PRE)
  public void setNumberOfHitsPerPage(BasicPagination searchElement, Query query) {
    query.hitsPerPage(searchElement.getHitsPerPage());
  }

  @SearchAdapterMethod(execute = Time.POST)
  public BasicPagination getPaginationSettings(BasicPagination searchElement,
      Query query, Response response) {

    Integer hitsPerPage = query.hitsPerPage();
    Long numberOfHits = response.getNumFound();

    // If rows in not set, we must guess it form resultset
    if (hitsPerPage == null) {
      hitsPerPage = response.getHits().size();
    }

    searchElement.setMaxPage((int) (Math.ceil(numberOfHits / hitsPerPage)));
    searchElement.setNumberOfHits(numberOfHits);
    searchElement.setHitsPerPage(hitsPerPage);
    return searchElement;
  }

  @SearchAdapterMethod(execute = Time.PRE)
  public void setPagination(BasicPagination searchElement,
      PageCondition condition, Query query) {
    query.offset(condition.page * searchElement.getHitsPerPage());
    query.hitsPerPage(searchElement.getHitsPerPage());
  }
}