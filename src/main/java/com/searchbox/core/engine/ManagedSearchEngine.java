package com.searchbox.core.engine;

import java.util.List;
import java.util.Set;

import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;

public interface ManagedSearchEngine {
	
	public boolean upateAllFields(List<FieldAttribute> fieldAttributes);
	
	public String getKeyForField(FieldAttribute fieldAttribute);
		
	public String getKeyForField(FieldAttribute fieldAttribute, USE operation);

	Set<String> getAllKeysForField(FieldAttribute fieldAttribute);
	
	public void reloadEngine();

	public void register();


}
