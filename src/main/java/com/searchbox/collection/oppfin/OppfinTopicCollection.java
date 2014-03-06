package com.searchbox.collection.oppfin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.dm.Field;

public class OppfinTopicCollection {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OppfinTopicCollection.class);
	
	/**
	 * From the crawler
	 * doc.addField("id", topicIdentifier );
			doc.addField("title", (String)topicObject.get("title"));
			doc.addField("descriptionRaw", topicDetailRaw );
			doc.addField("descriptionHtml", topicDetailHtml );
			doc.addField("docType", "Topic H2020");
			doc.addField("programme", "H2020");
			
			doc.addField("tags", topicObject.get("tags").toString());
			doc.addField("flags", topicObject.get("flags").toString());

			doc.addField("callTitle", (String)topicObject.get("callTitle"));
			doc.addField("callIdentifier", (String)topicObject.get("callIdentifier"));
			doc.addField("callDeadline", (Long)topicObject.get("callDeadline"));
			doc.addField("callStatus", (String)topicObject.get("callStatus"));
	 */
	
	public static List<Field> GET_FIELDS(){
		List<Field> fields = new ArrayList<Field>();
		fields.add(new Field(String.class, "id"));
		fields.add(new Field(String.class, "title"));
		fields.add(new Field(String.class, "descriptionHtml"));
		fields.add(new Field(String.class, "descriptionRaw"));
		fields.add(new Field(String.class, "docType"));
		fields.add(new Field(String.class, "programme"));
		fields.add(new Field(String.class, "tags"));
		fields.add(new Field(String.class, "flags"));
		fields.add(new Field(String.class, "callTitle"));
		fields.add(new Field(String.class, "callIdentifier"));
		fields.add(new Field(Date.class, "callDeadline"));
		fields.add(new Field(String.class, "callStatus"));
		return fields;
	}

}
