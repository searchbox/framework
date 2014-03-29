package test.searchbox.framework.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.SearchElement;
import com.searchbox.core.SearchElementBean;
import com.searchbox.framework.model.MissingClassAttributeException;
import com.searchbox.framework.model.SearchElementEntity;

public class SearchElementEntityTest {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(SearchElementEntityTest.class);

  MyElement myElement;

  public static class MyElement extends SearchElementBean {

    @SearchAttribute
    private String myAttribute;

    public String getMyAttribute() {
      return myAttribute;
    }

    public void setMyAttribute(String myAttribute) {
      this.myAttribute = myAttribute;
    }
  }

  @Before
  public void setUp() {
    myElement = new MyElement();
  }

  private static final String COLLECTION_NAME = "MyCollectionName";
  private static final String COLLECTION_ATTR = "MyCollectionAttr";

  @Test(expected = MissingClassAttributeException.class)
  public void testMissingClassAttributeException() {
    SearchElementEntity cf = new SearchElementEntity();
    cf.build();
    LOGGER.info("This should have been thrown!!!");
  }

  @Test(expected = MissingClassAttributeException.class)
  public void testBuildCollectionFactory_ByType() {
    SearchElementEntity<MyElement> cf = new SearchElementEntity<MyElement>();
    MyElement element = cf.build();
    Assert.assertNotNull("Did build MyElement Element", element);
    Assert.assertTrue("Class of Default is MyElement",
        MyElement.class.isAssignableFrom(element.getClass()));
  }

  @Test
  public void testBuildCollectionFactory() {
    SearchElementEntity cf = new SearchElementEntity()
        .setClazz(MyElement.class);
    SearchElement element = cf.build();
    Assert.assertNotNull("Did build MyElement Element", element);
    Assert.assertTrue("Class of element is MyElement",
        MyElement.class.isAssignableFrom(element.getClass()));
  }

  @Test
  public void testCustomCollectionBuild_NoType() {
    SearchElementEntity cf = new SearchElementEntity()
        .setClazz(MyElement.class);
    SearchElement my = cf.build();
    Assert.assertTrue("Class of Collection is MyElement",
        MyElement.class.isAssignableFrom(my.getClass()));
  }

  // @Test
  // public void testCustomCollectionBuild_Type(){
  // SearchElementEntity<MyElement> cf = new
  // SearchElementEntity<MyElement>().setClazz(MyElement.class);
  // MyElement my = cf.build();
  // Assert.assertTrue("Class of Collection is MyCollection",
  // MyElement.class.isAssignableFrom(my.getClass()));
  // }
  //
  //
  // @Test
  // public void testBeanFieldCopyInCollectionFactory(){
  //
  // SearchElementEntity<MyElement> cf = new SearchElementEntity<MyElement>()
  // .setLabel(COLLECTION_NAME)
  // .setClazz(MyElement.class)
  // .setAttribute("myAttribute",COLLECTION_ATTR);
  //
  // MyElement my = cf.build();
  //
  // Assert.assertEquals("Element Label (bean Copy)", COLLECTION_NAME,
  // my.getLabel());
  // }
  //
  // @Test
  // public void testAttributeCopyInCollectionFactory(){
  // SearchElementEntity<MyElement> cf = new SearchElementEntity<MyElement>()
  // .setLabel(COLLECTION_NAME)
  // .setClazz(MyElement.class)
  // .setAttribute("myAttribute",COLLECTION_ATTR);
  //
  // MyElement my = cf.build();
  //
  // Assert.assertEquals("Collection Attribute (attribute Copy)",
  // COLLECTION_ATTR, my.getMyAttribute());
  // }
}
