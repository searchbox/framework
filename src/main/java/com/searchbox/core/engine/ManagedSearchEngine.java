package com.searchbox.core.engine;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.MultiCollection;

public interface ManagedSearchEngine {

  public boolean updateDataModel(Collection collection, List<FieldAttribute> fieldAttributes);

  public void reloadEngine(Collection collection);

  public void register(Collection collection);
  
  public void reloadPlugins(Collection collection);

}
