package com.searchbox.core.engine;

import java.util.List;

import com.searchbox.core.dm.FieldAttribute;

public interface ManagedSearchEngine {

  public boolean updateDataModel(List<FieldAttribute> fieldAttributes);

  public void reloadEngine();

  public void register();

  public void reloadPlugins();
}
