package com.searchbox.core.dm;

import java.util.List;

import com.searchbox.core.engine.SearchEngine;

public interface Collection {

  /**
   * @return the Collection's Name. Used for batchs and what not.
   */
  public abstract String getName();

  /**
   * @return the Collection's Description
   */
  public abstract String getDescription();


  /**
   * @return the SearchEngine used for this collection
   */
  public abstract SearchEngine<?, ?> getSearchEngine();

  /**
   * @return the idFieldName
   */
  public abstract String getIdFieldName();

}