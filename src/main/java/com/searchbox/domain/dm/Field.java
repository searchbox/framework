package com.searchbox.domain.dm;
import javax.persistence.ManyToOne;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findFieldsByFieldTypeEquals", "findFieldsByKeyEqualsAndCollectionEquals" })
public class Field {

    /**
     */
    private String key;

    /**
     */
    private String label;

    //    @ManyToOne(targetEntity=FieldType.class)
    //    private FieldType type;
    @ManyToOne(targetEntity = Collection.class)
    private Collection collection;

    public Field(String key) {
        this.key = key;
    }
}
