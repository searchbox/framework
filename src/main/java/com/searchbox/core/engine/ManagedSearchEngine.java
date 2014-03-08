package com.searchbox.core.engine;

import java.util.Set;

import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;

public interface ManagedSearchEngine {
	
	public boolean updateForField(FieldAttribute fieldAttribute);
	
	public String getKeyForField(FieldAttribute fieldAttribute);
		
	public String getKeyForField(FieldAttribute fieldAttribute, USE operation);

	Set<String> getAllKeysForField(FieldAttribute fieldAttribute);
	
	public void reloadEngine();

	public void register();
}
