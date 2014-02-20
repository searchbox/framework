package com.searchbox.data;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.searchbox.app.domain.CollectionDefinition;
import com.searchbox.app.domain.FieldDefinition;
import com.searchbox.app.domain.PresetDefinition;
import com.searchbox.app.domain.PresetFieldAttributeDefinition;
import com.searchbox.app.domain.SearchElementDefinition;
import com.searchbox.app.domain.Searchbox;
import com.searchbox.app.repository.SearchboxRepository;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.debug.SolrToString;
import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.core.search.paging.BasicPagination;
import com.searchbox.core.search.query.EdismaxQuery;
import com.searchbox.core.search.result.HitList;
import com.searchbox.core.search.result.TemplatedHitList;
import com.searchbox.core.search.sort.FieldSort;
import com.searchbox.core.search.stat.BasicSearchStats;
import com.searchbox.ref.Order;
import com.searchbox.ref.Sort;
import com.searchbox.service.SearchEngineService;

@Component
public class BootStrap implements ApplicationListener<ContextRefreshedEvent> {

	Logger logger = LoggerFactory.getLogger(BootStrap.class);
	
	@Autowired
	private SearchEngineService searchEngineService;
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private SearchboxRepository repository;
	
	private static boolean BOOTSTRAPED = false;

	@Override
	@Transactional
	synchronized public void onApplicationEvent(ContextRefreshedEvent event) {

		if(BOOTSTRAPED){
			return;
		} 
		
		BOOTSTRAPED = true;
		
		logger.info("Bootstraping application");
		
		//The base Searchbox.
		Searchbox searchbox = new Searchbox("pubmed","Embeded pubmed Demo");
		
		//The base collection for searchbox
		CollectionDefinition collection = new CollectionDefinition("pubmed");
		ArrayList<FieldDefinition> collectionFields = new ArrayList<FieldDefinition>();
		collectionFields.add(FieldDefinition.StringFieldDef("id"));
		collectionFields.add(FieldDefinition.StringFieldDef("article-title"));
		collectionFields.add(FieldDefinition.StringFieldDef("article-abstract"));
		collection.setFieldDefinitions(collectionFields);		
		
		
		//SearchAll preset
		PresetDefinition preset = new PresetDefinition(searchbox, collection);
		preset.setLabel("Search All");
		preset.setSlug("all");
		PresetFieldAttributeDefinition fieldAttr = new PresetFieldAttributeDefinition(collection.getFieldDefinition("article-title"));
		fieldAttr.setSearchable(true);
		preset.addFieldAttributeDefinition(fieldAttr);
		PresetFieldAttributeDefinition fieldAttr2 = new PresetFieldAttributeDefinition(collection.getFieldDefinition("article-abstract"));
		fieldAttr2.setSearchable(true);
		preset.addFieldAttributeDefinition(fieldAttr2);

		//Create & add a TemplatedHitLIst SearchComponent to the preset;
		SearchElementDefinition templatedHitList = new SearchElementDefinition(TemplatedHitList.class);
		templatedHitList.setAttributeValue("titleField", "article-title");
		templatedHitList.setAttributeValue("idField", "id");
		templatedHitList.setAttributeValue("urlField", "article-title");
		templatedHitList.setAttributeValue("template", "<a href=\"${hit.getUrl()}\"><h5 class=\"result-title\">${hit.getTitle()}</h5></a>"+
														"<div>${hit.fieldValues['article-abstract']}</div>");
		preset.addSearchElementDeifinition(templatedHitList);

		//Create & add a FieldSort SearchComponent to the preset;
		SearchElementDefinition fieldSort = new SearchElementDefinition(FieldSort.class);
		SortedSet<FieldSort.Value> sortFields = new TreeSet<FieldSort.Value>();
		sortFields.add(FieldSort.getRelevancySort());
		sortFields.add(new FieldSort.Value("Latest Article", "article-completion-date", Sort.DESC));
		sortFields.add(new FieldSort.Value("Latest Reviewed", "article-revision-date", Sort.DESC));
		fieldSort.setAttributeValue("values", sortFields);
		preset.addSearchElementDeifinition(fieldSort);
				
		
		

				
		//Create & add a HitLIst SearchComponent to the preset;
//		SearchElementDefinition hitList = new SearchElementDefinition(HitList.class);
//		hitList.setAttributeValue("titleField", "article-title");
//		hitList.setAttributeValue("idField", "id");
//		hitList.setAttributeValue("urlField", "article-title");
//		ArrayList<String> fields = new ArrayList<String>();
//		fields.add("article-abstract");
//		fields.add("author");
//		fields.add("publication-type");
//		fields.add("article-completion-date");
//		fields.add("article-revision-date");
//		hitList.setAttributeValue("fields", fields);
//		preset.addSearchElementDeifinition(hitList);
		
		//Create & add a basicSearchStat SearchComponent to the preset;
		SearchElementDefinition basicStatus = new SearchElementDefinition(BasicSearchStats.class);
		preset.addSearchElementDeifinition(basicStatus);
		
		//Create & add a querydebug SearchComponent to the preset;
		SearchElementDefinition querydebug = new SearchElementDefinition(SolrToString.class);
		preset.addSearchElementDeifinition(querydebug);
		
		//Create & add a query SearchComponent to the preset;
		SearchElementDefinition query = new SearchElementDefinition(EdismaxQuery.class);
		preset.addSearchElementDeifinition(query);
		
		//Create & add a facet to the preset.
		SearchElementDefinition fieldFacet = new SearchElementDefinition(FieldFacet.class);
		fieldFacet.setAttributeValue("fieldName", "publication-type");
		fieldFacet.setAttributeValue("label", "Type");
		fieldFacet.setAttributeValue("order", Order.BY_VALUE);
		fieldFacet.setAttributeValue("sort", Sort.DESC);
		preset.addSearchElementDeifinition(fieldFacet);
		
		
		SearchElementDefinition pagination = new SearchElementDefinition(BasicPagination.class);
		preset.addSearchElementDeifinition(pagination);
		
		searchbox.addPresetDefinition(preset);
		
		PresetDefinition articles = new PresetDefinition(searchbox, collection);
		articles.setLabel("Articles");
		articles.setSlug("articles");
		searchbox.addPresetDefinition(articles);

		PresetDefinition press = new PresetDefinition(searchbox, collection);
		press.setLabel("Press");
		press.setSlug("press");
		searchbox.addPresetDefinition(press);
		
		repository.save(searchbox);
		
		//Making another Searchbox for testing and UI.
		Searchbox anotherSearchbox = new Searchbox("custom","My Searchbox");
		repository.save(anotherSearchbox);


//		SolrCloudEngine solr = new SolrCloudEngine();
//		solr.setName("test-collection");
//		try {
//			solr.setZkHost(new URL("http://www.zk.com/"));
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		solr.persist();

//		CollectionDefinition collection = new CollectionDefinition("Test");
//		collection.addField(new Field("id"));
//		collection.persist();
		
		
		
		

//		Preset searchAll = new Preset("Search All", null);
////		searchAll.setCollection(collection);
//		testSearchbox.addPreset(searchAll);
//		
//		//Create & add a HitLIst SearchComponent to the preset;
//		SearchElementDefinition hitList = new SearchElementDefinition(HitList.class);
//		hitList.setAttributeValue("titleField", "article-title");
//		hitList.setAttributeValue("urlField", "article-title");
//		ArrayList<String> fields = new ArrayList<String>();
//		fields.add("article-abstract");
//		fields.add("author");
//		fields.add("publication-type");
//		fields.add("article-completion-date");
//		fields.add("article-revision-date");
//		hitList.setAttributeValue("fields", fields);
//		searchAll.addSearchElement(hitList);
//		
//		//Create & add a basicSearchStat SearchComponent to the preset;
//		SearchElementDefinition basicStatus = new SearchElementDefinition(BasicSearchStats.class);
//		searchAll.addSearchElement(basicStatus);
//		
//		//Create & add a querydebug SearchComponent to the preset;
//		SearchElementDefinition querydebug = new SearchElementDefinition(SolrToString.class);
//		searchAll.addSearchElement(querydebug);
//		
//		//Create & add a query SearchComponent to the preset;
//		SearchElementDefinition query = new SearchElementDefinition(SimpleQuery.class);
//		searchAll.addSearchElement(query);
//
//		//Create & add a facet to the preset.
//		SearchElementDefinition fieldFacet = new SearchElementDefinition(FieldFacet.class);
//		fieldFacet.setAttributeValue("fieldName", "publication-type");
//		fieldFacet.setAttributeValue("label", "Type");
//		searchAll.addSearchElement(fieldFacet);
//
////		Preset searchVideos = new Preset("Videos", collection);
////		testSearchbox.addPreset(searchVideos);
////
////		Preset searchDoc = new Preset("Documents", collection);
////		testSearchbox.addPreset(searchDoc);
//
//		testSearchbox.persist();
//
//		for (Preset preset : testSearchbox.getPresets()) {
//			logger.info("Addded preset: " + preset.getLabel() + " with position: "
//					+ preset.getPosition());
//		}
//
////		for (Collection cc : Collection.findAllCollections()) {
////			logger.info("Got Collection: " + collection.getName()
////					+ " with engine: " + collection.getEngine().getClass());
////		}


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
		
//		System.out.println("XOXOXOXOXOXOXOXOXOXOXOXOX\nXOXOXOXOXOXOXOXOXOXOXOXOX");
//		System.out.println("List: " + Searchbox.findAllSearchboxes());
//		System.out.println("XOXOXOXOXOXOXOXOXOXOXOXOX\nXOXOXOXOXOXOXOXOXOXOXOXOX");
//		Searchbox sb = Searchbox.findAllSearchboxes().get(0);
//		System.out.println("Searchbox: " + sb);
//		System.out.println("XOXOXOXOXOXOXOXOXOXOXOXOX\nXOXOXOXOXOXOXOXOXOXOXOXOX");
//		System.out.println("Searchbox: " + sb.getPresets());
//		System.out.println("XOXOXOXOXOXOXOXOXOXOXOXOX\nXOXOXOXOXOXOXOXOXOXOXOXOX");
//
//		for(PresetDefinition pdef:sb.getPresets()){
//			Preset preset = pdef.getElement();
//			System.out.println("Preset: " + preset.getSlug());
//			for(SearchElement element:preset.getSearchElements()){
//				System.out.println("\tElement: " + element.getType()+"\t"+element.getLabel());
//			}
//		}
	}

}
