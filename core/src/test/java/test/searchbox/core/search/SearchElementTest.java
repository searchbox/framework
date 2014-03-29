package test.searchbox.core.search;

import org.junit.Assert;
import org.junit.Test;

import com.searchbox.core.SearchElement;
import com.searchbox.core.SearchElementBean;
import com.searchbox.core.search.query.EdismaxQuery;

public class SearchElementTest {

  @Test
  public void testElementType() {
    EdismaxQuery q = new EdismaxQuery();
    Assert.assertEquals("Edismax is a QUERY type", SearchElement.Type.QUERY,
        q.getType());
  }

  @Test
  public void testElementBeanType() {
    SearchElementBean q = new EdismaxQuery();
    Assert.assertEquals("Edismax is a QUERY type", SearchElement.Type.QUERY,
        q.getType());
  }

  @Test
  public void testElementInterfaceType() {
    SearchElement q = new EdismaxQuery();
    Assert.assertEquals("Edismax is a QUERY type", SearchElement.Type.QUERY,
        q.getType());
  }

}
