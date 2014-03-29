package com.searchbox.core.dm;

import com.searchbox.core.engine.SearchEngine;

public interface SearchableCollection {

  /**
   * @return the SearchEngine used for this collection
   */
  public SearchEngine<?, ?> getSearchEngine();

  public SearchableCollection setSearchEngine(SearchEngine<?,?> searchEngine);

}
