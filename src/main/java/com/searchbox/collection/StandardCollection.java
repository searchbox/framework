package com.searchbox.collection;

import java.util.Date;

import com.searchbox.core.dm.Collection.FieldMap;

public interface StandardCollection {
    
    public static final String STD_ID_FIELD = "sb_id";
    public static final String STD_TITLE_FIELD = "sb_title";
    public static final String STD_BODY_FIELD = "sb_body";
    public static final String STD_PUBLISHED_FIELD = "sb_published";
    public static final String STD_UPDATED_FIELD = "sb_udpated";
    
    String getIdValue(FieldMap fields);

    String getTitleValue(FieldMap fields);
        
    String getBodyValue(FieldMap fields);
    
    Date getPublishedValue(FieldMap fields);
    
    Date getUpdateValue(FieldMap fields);

}
