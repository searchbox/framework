package test.searchbox.engine.solr;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.searchbox.engine.solr.EmbeddedSolr;

public class EmbeddedSolrTest {

  EmbeddedSolr engine;

  @Before
  public void setSearchEngine() {
    engine = new EmbeddedSolr();
  }

  @After
  public void closeSearchEngine() throws Exception {
    engine.destroy();
  }

  @Test
  public void testSearchEngineLoaded() {
    Assert.assertTrue("SearchEngine did build correctly", engine != null);

  }

}
