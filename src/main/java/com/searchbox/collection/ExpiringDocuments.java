package com.searchbox.collection;

import java.util.Date;

import com.searchbox.core.dm.Collection.FieldMap;

public interface ExpiringDocuments {

  public static final String STD_DEADLINE_FIELD = "sb_deadline";

  Date getDeadlineValue(FieldMap fields);

}
