package com.searchbox.core.ref;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtilsTest {
  
  private static final Logger LOGGER = LoggerFactory
      .getLogger(StringUtilsTest.class);
  
  @Test
  public void testExtractHitFields() {
    Set<String> fields = StringUtils.extractHitFields("<a href=\"${hit.getUrl()}\"><h5 class=\"result-title\">${hit.getTitle()}</h5></a>"
        + "<div>${hit.fieldValues['article-abstract']}</div>" 
        + "<div>${hit.fieldValues['article-year']}</div>"
        + "<sbx:snippet hit=\"${hit}\" field=\"eenContentSummary\"/>"
        );
    
    Assert.assertTrue("Extracted 3 fields", fields.size()==3);
    Assert.assertTrue("Extracted ${hit.fieldValues['article-abstract']}",
        fields.contains("article-abstract"));
    Assert.assertTrue("Extracted field=\"eenContentSummary\"",
        fields.contains("eenContentSummary"));
  }
}
