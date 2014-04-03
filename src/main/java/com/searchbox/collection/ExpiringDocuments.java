package com.searchbox.collection;

import java.util.Date;

import com.searchbox.core.dm.FieldMap;

public interface ExpiringDocuments {

  public static final String STD_DEADLINE_FIELD = "deadline";

  Date getDeadlineValue(FieldMap fields);

}
