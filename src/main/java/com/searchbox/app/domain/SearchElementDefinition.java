package com.searchbox.app.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;

import com.searchbox.core.search.SearchElement;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class SearchElementDefinition extends UnknownClassDefinition 
	implements Comparable<SearchElementDefinition>, ElementFactory<SearchElement> {

	@NotNull
	@ManyToOne(targetEntity = PresetDefinition.class)
	private PresetDefinition preset;	

	private String label;
	
	private Integer position;
	
	public SearchElementDefinition() {
		super();
	}
	
	public SearchElementDefinition(Class<?> clazz) {
		super(clazz); 
	}

	public SearchElementDefinition(String label, Class<?> clazz) {
		super(clazz);
		this.label = label;
	}

	public PresetDefinition getPreset() {
		return preset;
	}

	public void setPreset(PresetDefinition preset) {
		this.preset = preset;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	@Override
	public int compareTo(SearchElementDefinition o) {
		return this.getPosition().compareTo(o.getPosition());
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	// TODO put that in a JUNIT
	public static void main(String... args) {
		// SearchElementDefinition fdef = new
		// SearchElementDefinition(FieldFacet.class);
		//
		// fdef.setAttributeValue("fieldName", "MyField");
		// fdef.setAttributeValue("label", "Hello World");
		//
		// for(DefinitionAttribute attr:fdef.getAttributes()){
		// System.out.println("Field["+attr.getType().getSimpleName()+"]\t" +
		// attr.getName()+"\t"+attr.getValue());
		// }
		//
		// SearchElement elem;
		// try {
		// elem = (FieldFacet) fdef.toElement((SearchElement)
		// fdef.getClazz().newInstance());
		// } catch (InstantiationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IllegalAccessException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// //System.out.println("element Label: " +
		// ((FieldFacet)elem.getLabel()));
	}

	@Override
	public SearchElement getInstance() {
		SearchElement element = (SearchElement) super.toObject();
		element.setLabel(this.getLabel());
		element.setPosition(this.getPosition());
		element.setDefinitionId(this.getId());
		return element;
	}
}
