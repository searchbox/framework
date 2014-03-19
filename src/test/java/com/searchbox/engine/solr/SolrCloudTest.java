//package com.searchbox.engine.solr;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.Assert;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.searchbox.core.dm.DefaultCollection;
//import com.searchbox.core.dm.MultiCollection;
//import com.searchbox.engine.SearchEngineConfig;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = SearchEngineConfig.class)
//public class SolrCloudTest {
//  
//  @Autowired
//  ApplicationContext context;
//  
//  SolrCloud engine;
//
//  @Before
//  public void setSearchEngine() {
//    engine = context.getAutowireCapableBeanFactory()
//                        .createBean(SolrCloud.class);
//  }
//  
////  @After
////  public void closeSearchEngine() throws Exception{
////    engine.destroy();
////  }
//
//  @Test
//  public void testSearchEngineLoaded(){
//    
//    Assert.assertTrue("SearchEngine did build correctly", engine != null);
//  }
//  
//  @Test
//  public void testRegisterMultiCollection(){
//    
//    //Building 2 default Collections
//    DefaultCollection collection1 = new DefaultCollection("collection1");
//    collection1.setSearchEngine(engine);
//    engine.register(collection1);
//    
//    DefaultCollection collection2 = new DefaultCollection("collection2");
//    collection2.setSearchEngine(engine);
//    engine.register(collection2);
//
//    
//    //Building the MultiCollection
//    MultiCollection multiCollection = new MultiCollection("multiCollection");
//    
//    Assert.assertNotNull("MultiCollection's collection list should not be null",
//        multiCollection.getCollections());
//    
//    //Adding default Collections to the multi
//    multiCollection.getCollections().add(collection1);
//    multiCollection.getCollections().add(collection2);
//
//    engine.register(multiCollection);
//  }
//}
