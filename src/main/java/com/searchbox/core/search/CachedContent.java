package com.searchbox.core.search;

public interface CachedContent {
	
	public int getContentHash();
	
	public String getContent();
	
	public void setPath(String path);

}
