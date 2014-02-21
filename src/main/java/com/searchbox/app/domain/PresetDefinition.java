package com.searchbox.app.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.Order;

import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.core.search.query.EdismaxQuery;
import com.searchbox.core.search.result.HitList;

@Entity
public class PresetDefinition {

	private static Logger logger = LoggerFactory.getLogger(PresetDefinition.class);
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;
	
	@ManyToOne
	private Searchbox searchbox;

	@ManyToOne
	private CollectionDefinition collection;

	@OneToMany(mappedBy="preset", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@Order
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
	
	public PresetDefinition(){
		searchElements = new HashSet<SearchElementDefinition>();
		fieldAttributes = new HashSet<PresetFieldAttributeDefinition>();
	}

	public PresetDefinition(Searchbox searchbox, CollectionDefinition collection) {
		this.searchbox = searchbox;
		this.collection = collection;
		searchElements = new HashSet<SearchElementDefinition>();
		fieldAttributes = new HashSet<PresetFieldAttributeDefinition>(); 
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Searchbox getSearchbox() {
		return searchbox;
	}

	public void setSearchbox(Searchbox searchbox) {
		this.searchbox = searchbox;
	}

	public CollectionDefinition getCollection() {
		return collection;
	}

	public void setCollection(CollectionDefinition collection) {
		this.collection = collection;
	}

	public Set<SearchElementDefinition> getSearchElements() {
		return searchElements;
	}

	public void setSearchElements(Set<SearchElementDefinition> searchElements) {
		this.searchElements = searchElements;
	}

	public Set<PresetFieldAttributeDefinition> getFieldAttributes() {
		return fieldAttributes;
	}

	public void setFieldAttributes(
			Set<PresetFieldAttributeDefinition> fieldAttributes) {
		this.fieldAttributes = fieldAttributes;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getGlobal() {
		return global;
	}

	public void setGlobal(Boolean global) {
		this.global = global;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Preset toPreset(Preset preset){
		BeanUtils.copyProperties(this, preset);
		return preset;
	}
	
	public void addSearchElementDeifinition(SearchElementDefinition definition) {
		definition.setPreset(this);
		definition.setPosition(this.searchElements.size()+1);
		this.searchElements.add(definition);
		
	}
	
	
	public void addFieldAttributeDefinition(PresetFieldAttributeDefinition definition) {
		boolean exists = false;
		for(PresetFieldAttributeDefinition attr:fieldAttributes){
			if(attr.getField().equals(definition)){
				BeanUtils.copyProperties(definition, attr);
				exists = true;
			}
		}
		if(!exists){
			definition.setPreset(this);
			this.fieldAttributes.add(definition);
		}
	}
	
	public PresetFieldAttributeDefinition getFieldAttributeDefinitionByKey(String key){
		for(PresetFieldAttributeDefinition adef:this.fieldAttributes){
			if(adef.getField().getKey().equals(key)){
				return adef;
			}
		}
		return null;
	}
	
	public PresetFieldAttributeDefinition getFieldAttributeDefinitionByField(FieldDefinition field){
		for(PresetFieldAttributeDefinition adef:this.fieldAttributes){
			if(adef.getField().equals(field)){
				return adef;
			}
		}
		return null;
	}

	public static PresetDefinition BasicPreset(Searchbox sb, CollectionDefinition collection){
		PresetDefinition pdef = new PresetDefinition(sb, collection);
		pdef.slug = "all";
		pdef.label = "Basic Preset";
		
		SearchElementDefinition query = new SearchElementDefinition("EdismaxQuery", EdismaxQuery.class);
		pdef.addSearchElementDeifinition(query);
		
		SearchElementDefinition result = new SearchElementDefinition("HitList", HitList.class);
		pdef.addSearchElementDeifinition(result);
		
		return pdef;
	}
	
	@PrePersist
	public void checkPresetAttributes(){
		//THis is for a SearchEngine Managed Collection!!!
		for(FieldDefinition fdef:collection.getFieldDefinitions()){
			boolean exists = false;
			for(PresetFieldAttributeDefinition attr:fieldAttributes){
				if(attr.getField().equals(fdef)){
					exists = true;
				}
			}
			if(!exists){
				this.addFieldAttributeDefinition(new PresetFieldAttributeDefinition(fdef));
			}
		}
	}

	// TODO put that in a JUNIT
	public static void main(String... args) {
		
		Searchbox sb = new Searchbox("test","testing");
		sb.setSlug("pubmed");
		
		//The base collection for searchbox
		CollectionDefinition collection = new CollectionDefinition("testCollection");
		collection.setName("pubmed");
		ArrayList<FieldDefinition> collectionFields = new ArrayList<FieldDefinition>();
		collectionFields.add(FieldDefinition.StringFieldDef("id"));
		collectionFields.add(FieldDefinition.StringFieldDef("title"));
		collectionFields.add(FieldDefinition.StringFieldDef("article-abstract"));
		collection.setFieldDefinitions(collectionFields);

		PresetDefinition pdef = PresetDefinition.BasicPreset(sb, collection);

		pdef.slug = "search-all";
		pdef.label = "Hello World";
		
		SearchElementDefinition fdef = new SearchElementDefinition("FieldFacet", FieldFacet.class);
		fdef.setAttributeValue("fieldName", "MyField");
		fdef.setAttributeValue("label", "Categories");
		pdef.addSearchElementDeifinition(fdef);
		
		PresetFieldAttributeDefinition fieldAttr = new PresetFieldAttributeDefinition(collection.getFieldDefinition("title"));
		fieldAttr.setSearchable(true);
		pdef.addFieldAttributeDefinition(fieldAttr);
		
//		Preset elem = pdef.getElement();
//		System.out.println("Preset label: " + elem.getLabel());
//		System.out.println("Preset slug: " + elem.getSlug());
//		
//		for(SearchElement element:elem.getSearchElements()){
//			System.out.println("SearchElement label: " + element.getLabel());
//		}
//		
//		for(PresetFieldAttribute element:elem.getFieldAttributes()){
//			System.out.println("PresetFieldAttribute: " + element);
//		}
	}
}
