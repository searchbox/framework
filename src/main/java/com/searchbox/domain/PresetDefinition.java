package com.searchbox.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.core.search.query.SimpleQuery;
import com.searchbox.core.search.result.HitList;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class PresetDefinition extends Definition<Preset> {

	@ManyToOne
	private Searchbox searchbox;

	@ManyToOne
	private CollectionDefinition collection;

	@OneToMany(mappedBy="preset", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<SearchElementDefinition> searchElements;

	public PresetDefinition(Searchbox searchbox, CollectionDefinition collection) {
		super(Preset.class);
		searchElements = new HashSet<SearchElementDefinition>();
	}
	
	@Override
	public Preset getElement(){
		Preset preset = super.getElement();
		for(SearchElementDefinition elementDef:searchElements){
			preset.addSearchElement(elementDef.getElement());
		}
		return preset;
	}
	
	public void addSearchElementDeifinition(SearchElementDefinition definition) {
		definition.setPreset(this);
		this.searchElements.add(definition);
		
	}

	public static PresetDefinition BasicPreset(Searchbox sb, CollectionDefinition collection){
		PresetDefinition pdef = new PresetDefinition(sb, collection);
		pdef.setAttributeValue("slug", "all");
		pdef.setAttributeValue("label", "Basic Preset");
		
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

		pdef.setAttributeValue("slug", "search-all");
		pdef.setAttributeValue("label", "Hello World");

		for (DefinitionAttribute attr : pdef.getAttributes()) {
			System.out.println("Field[" + attr.getType().getSimpleName()
					+ "]\t" + attr.getName() + "\t" + attr.getValue());
		}

		
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
