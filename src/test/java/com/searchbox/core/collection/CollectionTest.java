package com.searchbox.core.collection;


import org.junit.Test;
import org.junit.Assert;

import com.searchbox.core.dm.DefaultCollection;
import com.searchbox.core.dm.MultiCollection;
import com.searchbox.core.dm.SearchableCollection;

public class CollectionTest {

  
  @Test
  public void testDefaultCollectionIsSearchable(){
    DefaultCollection collection = new DefaultCollection();
    Assert.assertTrue("Default collection implements SearchableColleciton",
        SearchableCollection.class.isAssignableFrom(collection.getClass()));
  }
  
  @Test
  public void testMultiCollectionIsSearchable(){
    MultiCollection collection = new MultiCollection();
    Assert.assertTrue("Multi collection implements SearchableColleciton",
        SearchableCollection.class.isAssignableFrom(collection.getClass()));
  }
}
