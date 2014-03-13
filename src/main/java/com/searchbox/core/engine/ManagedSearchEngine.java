package com.searchbox.core.engine;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;

public interface ManagedSearchEngine {

    public boolean updateDataModel(List<FieldAttribute> fieldAttributes);

    public void reloadEngine();

    public void register();

    public void reloadPlugins();
}
