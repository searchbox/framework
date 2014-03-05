package com.searchbox.core.engine;

import java.util.List;
import java.util.Set;

import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldAttribute;

public interface ManagedSearchEngine {
	
	public boolean updateForField(Field field, FieldAttribute fieldAttribute);
	
	public Set<String> getKeyForField(Field field, FieldAttribute fieldAttribute);

	public String getKeyForField(Field field, FieldAttribute fieldAttribute, String operation);

}
