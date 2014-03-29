package com.searchbox.core.dm;

import java.util.List;

public interface CollectionWithFields {

  /**
   * @return the Fields for the collection
   */
  public abstract List<Field> getFields();

}
