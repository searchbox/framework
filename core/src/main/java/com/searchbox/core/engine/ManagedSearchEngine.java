package com.searchbox.core.engine;

import java.util.List;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.FieldAttribute;

public interface ManagedSearchEngine {

  public boolean updateDataModel(Collection collection,
      List<FieldAttribute> fieldAttributes);

  public void reloadEngine(Collection collection);

  public void register(Collection collection);

  public void reloadPlugins(Collection collection);

}
