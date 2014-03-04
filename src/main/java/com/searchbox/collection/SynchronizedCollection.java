package com.searchbox.collection;

import java.util.Date;

public interface SynchronizedCollection {

	public Date getLastUpdate();
	
	public void synchronize();
	
}
