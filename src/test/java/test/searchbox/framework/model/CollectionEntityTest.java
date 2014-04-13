package test.searchbox.framework.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.DefaultCollection;
import com.searchbox.framework.model.CollectionEntity;
import com.searchbox.framework.model.MissingClassAttributeException;

public class CollectionEntityTest {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(CollectionEntityTest.class);

  Collection myCollection;

  public static class MyCollection extends DefaultCollection {

    @SearchAttribute
    private String myAttribute;

    public MyCollection() {

    }

    public String getMyAttribute() {
      return myAttribute;
    }

    public void setMyAttribute(String myAttribute) {
      this.myAttribute = myAttribute;
    }
  }

  @Before
  public void setUp() {
    myCollection = new MyCollection();
  }

  private static final String COLLECTION_NAME = "MyCollectionName";
  private static final String COLLECTION_ATTR = "MyCollectionAttr";

  @Test(expected = MissingClassAttributeException.class)
  public void testMissingClassAttributeException() {
    CollectionEntity cf = new CollectionEntity();
    cf.build();
    LOGGER.info("This should have been thrown!!!");
  }

  @Test(expected = MissingClassAttributeException.class)
  public void testBuildCollectionFactory_ByType() {
    CollectionEntity<MyCollection> cf = new CollectionEntity<MyCollection>();
    MyCollection coll = cf.build();
    Assert.assertNotNull("Did build default Collection", coll);
    Assert.assertTrue("Class of Default is DefaultCollection",
        MyCollection.class.isAssignableFrom(coll.getClass()));
  }

//  @Test
//  public void testBuildCollectionFactory() {
//    CollectionEntity cf = new CollectionEntity()
//        .setClazz(DefaultCollection.class);
//    Collection coll = cf.build();
//    Assert.assertNotNull("Did build default Collection", coll);
//    Assert.assertTrue("Class of Default is DefaultCollection",
//        DefaultCollection.class.isAssignableFrom(coll.getClass()));
//  }

//  @Test
//  public void testCustomCollectionBuild_NoType() {
//    CollectionEntity cf = new CollectionEntity().setClazz(MyCollection.class);
//    Collection my = cf.build();
//    Assert.assertTrue("Class of Collection is MyCollection",
//        MyCollection.class.isAssignableFrom(my.getClass()));
//  }

//  @Test
//  public void testCustomCollectionBuild_Type() {
//    CollectionEntity<MyCollection> cf = new CollectionEntity<MyCollection>()
//        .setClazz(MyCollection.class);
//    MyCollection my = cf.build();
//    Assert.assertTrue("Class of Collection is MyCollection",
//        MyCollection.class.isAssignableFrom(my.getClass()));
//  }

//  @Test
//  public void testBeanFieldCopyInCollectionFactory() {
//
//    CollectionEntity<MyCollection> cf = new CollectionEntity<MyCollection>()
//        .setName(COLLECTION_NAME).setClazz(MyCollection.class)
//        .setAttribute("myAttribute", COLLECTION_ATTR);
//
//    MyCollection my = cf.build();
//
//    Assert.assertEquals("Collection Name (bean Copy)", COLLECTION_NAME,
//        my.getName());
//  }

//  @Test
//  public void testAttributeCopyInCollectionFactory() {
//    CollectionEntity<MyCollection> cf = new CollectionEntity<MyCollection>()
//        .setName(COLLECTION_NAME).setClazz(MyCollection.class)
//        .setAttribute("myAttribute", COLLECTION_ATTR);
//
//    MyCollection my = cf.build();
//
//    Assert.assertEquals("Collection Attribute (attribute Copy)",
//        COLLECTION_ATTR, my.getMyAttribute());
//  }
}
