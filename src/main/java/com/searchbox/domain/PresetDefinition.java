package com.searchbox.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.core.search.query.SimpleQuery;
import com.searchbox.core.search.result.HitList;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class PresetDefinition {

	private static Logger logger = LoggerFactory.getLogger(PresetDefinition.class);
	
	@ManyToOne
	private Searchbox searchbox;

	@ManyToOne
	private CollectionDefinition collection;

	@OneToMany(mappedBy="preset", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<SearchElementDefinition> searchElements;
	
	@OneToMany(targetEntity=PresetFieldAttributeDefinition.class, cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<PresetFieldAttributeDefinition> fieldAttributes;
	
	/**
     */
	private String slug;

	/**
     */
	private String label;

	/**
     */
	private String description;

	/**
     */
	private Boolean global;

	/**
     */
	private Boolean visible;

	/**
     */
	private Integer position;

	public PresetDefinition(Searchbox searchbox, CollectionDefinition collection) {
		this.searchbox = searchbox;
		this.collection = collection;
		searchElements = new HashSet<SearchElementDefinition>();
		fieldAttributes = new HashSet<PresetFieldAttributeDefinition>(); 
	}
	
	public Preset getElement(){
		//Merge field values here. Use Reflection since I am a laxy bastard...
		Preset preset = new Preset();
//		for(SearchElementDefinition elementDef:searchElements){
//			try {
//				preset.addSearchElement(elementDef.getElement());
//			} catch (Exception e){
//				logger.error("Could not get searchElement with def: " + elementDef, e);
//			}
//		}
//		for(PresetFieldAttributeDefinition fieldDef:fieldAttributes){
//			try {
//				preset.addFieldAttribute(fieldDef.getElement());
//			} catch (Exception e){
//				logger.error("Could not get searchElement with def: " + fieldDef, e);
//			}
//		}
		return preset;
	}
	
	public void addSearchElementDeifinition(SearchElementDefinition definition) {
		definition.setPreset(this);
		this.searchElements.add(definition);
		
	}
	
	public void addFieldAttributeDefinition(PresetFieldAttributeDefinition definition) {
		definition.setPreset(this);
		this.fieldAttributes.add(definition);
		
	}

	public static PresetDefinition BasicPreset(Searchbox sb, CollectionDefinition collection){
		PresetDefinition pdef = new PresetDefinition(sb, collection);
		pdef.slug = "all";
		pdef.label = "Basic Preset";
		
		SearchElementDefinition query = new SearchElementDefinition(SimpleQuery.class);
		pdef.addSearchElementDeifinition(query);
		
		SearchElementDefinition result = new SearchElementDefinition(HitList.class);
		pdef.addSearchElementDeifinition(result);
		
		return pdef;
	}

	// TODO put that in a JUNIT
	public static void main(String... args) {
		Searchbox sb = new Searchbox();
		sb.setSlug("pubmed");
		
		CollectionDefinition collection = new CollectionDefinition();
		
		PresetDefinition pdef = PresetDefinition.BasicPreset(sb, collection);

		pdef.slug = "search-all";
		pdef.label = "Hello World";
		
		SearchElementDefinition fdef = new SearchElementDefinition(FieldFacet.class);
		fdef.setAttributeValue("fieldName", "MyField");
		fdef.setAttributeValue("label", "Categories");
		
		pdef.searchElements.add(fdef);
		
		Preset elem = pdef.getElement();
		System.out.println("Preset label: " + elem.getLabel());
		System.out.println("Preset slug: " + elem.getSlug());
		
		for(SearchElement element:elem.getSearchElements()){
			System.out.println("SearchElement label: " + element.getLabel());
		}
	}
}
