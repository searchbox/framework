package com.searchbox.data;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.searchbox.domain.app.Preset;
import com.searchbox.domain.app.Searchbox;
import com.searchbox.domain.app.facet.FieldFacetDefinition;
import com.searchbox.domain.dm.Collection;
import com.searchbox.domain.dm.Field;
import com.searchbox.domain.engine.SolrCloudEngine;

@Component
public class BootStrap implements ApplicationListener<ContextRefreshedEvent> {

	public static boolean bootstraped = false;

	Logger log = LoggerFactory.getLogger(BootStrap.class);

	@Override
	@Transactional
	synchronized public void onApplicationEvent(ContextRefreshedEvent event) {

		log.info("Bootstraping application");

		if (bootstraped)
			return;

		Searchbox testSearchbox = new Searchbox("test",
				"this is a test Searchbox");
		testSearchbox.persist();

		SolrCloudEngine solr = new SolrCloudEngine();
		solr.setName("test-collection");
		try {
			solr.setZkHost(new URL("http://www.zk.com/"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		solr.persist();

		Collection collection = new Collection("Test", solr);
		collection.addField(new Field("id"));
		collection.persist();

		Preset searchAll = new Preset("Search All", collection);
		testSearchbox.addPreset(searchAll);
		searchAll.addFacetDefinition(new FieldFacetDefinition(Field
				.findFieldsByKeyEqualsAndCollectionEquals("id", collection)
				.getSingleResult()));
		searchAll.persist();

		Preset searchVideos = new Preset("Videos", collection);
		testSearchbox.addPreset(searchVideos);

		Preset searchDoc = new Preset("Documents", collection);
		testSearchbox.addPreset(searchDoc);

		testSearchbox.persist();

		for (Preset preset : testSearchbox.getPresets()) {
			log.info("Addded preset: " + preset.getLabel() + " with position: "
					+ preset.getPosition());
		}

		for (Collection cc : Collection.findAllCollections()) {
			log.info("Got Collection: " + collection.getName()
					+ " with engine: " + collection.getEngine().getClass());
		}

		log.info("Bootstraping");

		bootstraped = true;
	}

	// private void setSettings() {
	// SettingCategory generalCategory = new SettingCategory();
	// generalCategory.setLabel("General");
	// generalCategory.setScope("searchbox");
	// generalCategory
	// .addSetting("Logo", "The searchbox logo used everywhere");
	// generalCategory.addSetting("Private",
	// "A private searchbox requires a registration", false);
	// generalCategory.addSetting("Advanced",
	// "Show advanced configuration (Solr schema/config)", false);
	// generalCategory.persist();
	//
	// SettingCategory customizationCategory = new SettingCategory();
	// customizationCategory.setLabel("Customization");
	// customizationCategory.setScope("searchbox");
	// customizationCategory.addSetting("Custom JS",
	// "Js script added at the bottom of the page");
	// customizationCategory.addSetting("Custom CSS",
	// "CSS added on the top of the page");
	// customizationCategory.addSetting("Site Verification",
	// "Google site verification for webmaster tools");
	// customizationCategory.persist();
	//
	// SettingCategory landingCategory = new SettingCategory();
	// landingCategory.setLabel("Landing Page");
	// landingCategory.setScope("searchbox");
	// landingCategory
	// .addSetting(
	// "Teaser",
	// "Teaser, html format that encourage users to signup to your searchbox (if private)");
	// landingCategory.persist();
	//
	// SettingCategory searchCategory = new SettingCategory();
	// searchCategory.setLabel("Search");
	// searchCategory.setScope("preset");
	// searchCategory.addSetting("Query Operator",
	// "Query operator to use between keywords", "AND");
	// searchCategory.addSetting("Semantic search",
	// "Activate semantic search", false);

	// settings["resultPerPage"] = new Setting(label:
	// "Pagination type","Type of pagination", slug:"paginationType",
	// category: searchCat, widget:"select",
	// options:"traditional=Traditional Pagination;infiniteScroll=Infinite scroll",
	// defaultValue:"traditional").save(failOnError: true, flush:true)
	// settings["semanticSearchActive"] = new Setting(label:
	// "Semantic search","Activate semantic search", slug:"semanticSearch",
	// category: searchCat, widget:"boolean",
	// defaultValue:"0").save(failOnError: true, flush:true)
	// settings["itemsPerPage"] = new Setting(label:
	// "Result per page","Number of result per page", slug:"pageSize",
	// category: searchCat, widget:"integer",
	// defaultValue:"10").save(failOnError: true, flush:true)
	// settings["queryOperator"] = new Setting(label:
	// "Query operator","Query operator to use between keywords", slug:
	// "queryOperator", category:searchCat, widget:"radio",
	// options:"OR=OR;AND=AND", defaultValue:"AND").save(failOnError: true,
	// flush:true)
	// settings["liveSearch"] = new Setting(label:
	// "Live search","Enable live search (search as you type)", slug:
	// "liveSearch", category:searchCat, widget:"boolean",
	// defaultValue:"0").save(failOnError: true, flush:true)
	// settings["rssLink"] = new Setting(label:
	// "Rss Link","Add a link to RSS feed for results", slug:"rssLink",
	// category:searchCat,
	// widget:"boolean",defaultValue:"0").save(failOnError: true,
	// flush:true)

	// searchCategory.persist();

	// }

	public static void main(String... args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:META-INF/spring/applicationContext.xml");

		System.out.println("Hello");
	}

}
