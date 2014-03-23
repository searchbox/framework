package com.searchbox.engine.solr;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.searchbox.engine.SearchEngineConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SearchEngineConfig.class)
public class EmbeddedSolrTest {
  
  @Autowired
  ApplicationContext context;
  
  EmbeddedSolr engine;

  @Before
  public void setSearchEngine() {
    engine = context.getAutowireCapableBeanFactory()
                        .createBean(EmbeddedSolr.class);
  }
  
  @After
  public void closeSearchEngine() throws Exception{
    engine.destroy();
  }
  
  @Test
  public void testSearchEngineLoaded(){
    Assert.assertTrue("SearchEngine did build correctly", engine != null);

  }

 

}
