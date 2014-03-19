package com.searchbox.collection;

import java.util.Date;

import com.searchbox.core.dm.FieldMap;

public interface StandardCollection {

  public static final String STD_ID_FIELD = "id";
  public static final String STD_TITLE_FIELD = "title";
  public static final String STD_BODY_FIELD = "body";
  public static final String STD_PUBLISHED_FIELD = "published";
  public static final String STD_UPDATED_FIELD = "udpated";

  String getIdValue(FieldMap fields);

  String getTitleValue(FieldMap fields);

  String getBodyValue(FieldMap fields);

  Date getPublishedValue(FieldMap fields);

  Date getUpdateValue(FieldMap fields);

}
