package com.searchbox.app.domain;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.SearchElement;
import com.searchbox.ref.ReflectionUtils;

@Entity
@Configurable
public class SearchElementDefinition implements ApplicationContextAware, Comparable<SearchElementDefinition>{

	private static Logger logger = LoggerFactory.getLogger(SearchElementDefinition.class);
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;
	
	private Class<?> clazz;
	
	private Integer position;

	@NotNull
	@ManyToOne(targetEntity=PresetDefinition.class)
	private PresetDefinition preset;
	
	@OneToMany(targetEntity=DefinitionAttribute.class, cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DefinitionAttribute> attributes;
	
	@Transient
	ApplicationContext context;
	
	public SearchElementDefinition(){
		
	}
	
	public SearchElementDefinition(Class<?> searchElementClass){
		this.clazz = searchElementClass;
		this.attributes = new ArrayList<DefinitionAttribute>();
		ReflectionUtils.inspectAndSaveAttribute(clazz, attributes);
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

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public PresetDefinition getPreset() {
		return preset;
	}

	public void setPreset(PresetDefinition preset) {
		this.preset = preset;
	}

	public void setAttributes(List<DefinitionAttribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
	}

	public SearchElement toElement(Preset preset, SearchElement element){
		try {
			element.setPosition(this.getPosition());
			element.setDefinitionId(this.getId());
			for(DefinitionAttribute attribute:attributes){
				if(attribute.getValue() != null){
					Method setter = null;
					try {
						setter = new PropertyDescriptor(attribute.getName(), element.getClass()).getWriteMethod();
						if(setter == null){
							logger.error("Could not find setter: " + element.getClass().getName()+"#"+attribute.getName());
						} else {
							setter.invoke(element, attribute.getValue());
						}
	//					Field field = ReflectionUtils.findUnderlying(clazz, attribute.getName());
	//					field.setAccessible(true);
	//					field.set(element, attribute.getValue());
					} catch (Exception e) {
						logger.error("Could not find setter: " + element.getClass().getName()+
								"#"+attribute.getName()+"["+attribute.getType().getName()+"]");
						logger.error("Attribute Value is: " + attribute.getValue());
						logger.error("Attribute Value Class is: " + attribute.getValue().getClass().getName());
						if(setter != null){
							logger.error("\tsetter args: " + 
								setter.getParameterTypes()[0].getName());
						}
					}
				}
			}
			return element;
		} catch (Exception e){
			logger.error("Could not get Element for class: " + clazz, e);
		}
		throw new RuntimeException("Could not construct element for class: " + clazz);
	}
	
	public List<DefinitionAttribute> getAttributes(){
		return this.attributes;
	}
	
	public DefinitionAttribute getAttributeByName(String name){
		for(DefinitionAttribute attr:this.attributes){
			if(attr.getName().equals(name)){
				return attr;
			}
		}
		return null;
	}
	
	public SearchElementDefinition setAttributeValue(String name, Object value) {
		DefinitionAttribute attr = this.getAttributeByName(name);
		if(attr != null){
			this.getAttributeByName(name).setValue(value);
		} else {
			logger.error("Could not set Attribute \""+name+"\" for element: " + this.clazz.getSimpleName());
		}
		return this;
	}

	@Override
	public int compareTo(SearchElementDefinition o) {
		return this.position.compareTo(o.getPosition());
	}
	
	//TODO put that in a JUNIT
		public static void main(String... args){
//			SearchElementDefinition fdef = new SearchElementDefinition(FieldFacet.class);
//			
//			fdef.setAttributeValue("fieldName", "MyField");
//			fdef.setAttributeValue("label", "Hello World");
//			
//			for(DefinitionAttribute attr:fdef.getAttributes()){
//				System.out.println("Field["+attr.getType().getSimpleName()+"]\t" + attr.getName()+"\t"+attr.getValue());
//			}
//			
//			SearchElement elem;
//			try {
//				elem = (FieldFacet) fdef.toElement((SearchElement) fdef.getClazz().newInstance());
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//System.out.println("element Label: " + ((FieldFacet)elem.getLabel()));
		}
}
