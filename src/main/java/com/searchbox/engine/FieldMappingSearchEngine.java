package com.searchbox.engine;

import java.util.Set;

import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;

public interface FieldMappingSearchEngine {

  public String getKeyForField(FieldAttribute fieldAttribute);

  public String getKeyForField(FieldAttribute fieldAttribute, USE operation);

  Set<String> getAllKeysForField(FieldAttribute fieldAttribute);

}
