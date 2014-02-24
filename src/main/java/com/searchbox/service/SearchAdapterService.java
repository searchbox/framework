package com.searchbox.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.searchbox.anno.SearchAdapter;
import com.searchbox.anno.SearchAdapterMethod;
import com.searchbox.anno.SearchAdapterMethod.Target;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.search.SearchElement;

@Service
public class SearchAdapterService implements
		ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LoggerFactory
			.getLogger(SearchAdapterService.class);

	@Autowired
	ApplicationContext context;

	private Map<Class<?>, Object> adapterIntances;
	private Map<Object, Set<Method>> preSearchMethods;
	private Map<Object, Set<Method>> postSearchMethods;

	public SearchAdapterService() {
		this.adapterIntances = new HashMap<Class<?>, Object>();
		this.preSearchMethods = new HashMap<Object, Set<Method>>();
		this.postSearchMethods = new HashMap<Object, Set<Method>>();
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		// Reset maps for adapters.
		this.adapterIntances = new HashMap<Class<?>, Object>();
		this.preSearchMethods = new HashMap<Object, Set<Method>>();
		this.postSearchMethods = new HashMap<Object, Set<Method>>();

		// Scan the classpath for adapters
		for (Entry<String, Object> bean : context.getBeansWithAnnotation(
				SearchAdapter.class).entrySet()) {
			Object adapter = bean.getValue();
			Class<?> target = adapter.getClass()
					.getAnnotation(SearchAdapter.class).target();
			this.adapterIntances.put(target, adapter);
			this.preSearchMethods.put(adapter, new HashSet<Method>());
			this.postSearchMethods.put(adapter, new HashSet<Method>());
			logger.debug("Found adapter for: " + target.getSimpleName());
			for (Method method : adapter.getClass().getDeclaredMethods()) {
				if (method.isAnnotationPresent(SearchAdapterMethod.class)) {
					if (method.getAnnotation(SearchAdapterMethod.class)
							.target().equals(Target.PRE)) {
						logger.debug("Registering Pre adapt: "
								+ method.getName());
						this.preSearchMethods.get(adapter).add(method);
					} else {
						logger.debug("Registering Post adapt: "
								+ method.getName());
						this.postSearchMethods.get(adapter).add(method);
					}
				}
			}
		}
	}

	public void doPreSearchAdapt(Collection<SearchElement> elements,
			SearchEngine<?, ?> engine, Object... objects) {
		for (SearchElement element : elements) {
			doPreSearchAdapt(element, engine, objects);
		}
	}

	public void doPreSearchAdapt(SearchElement element,
			SearchEngine<?, ?> engine, Object... objects) {
		if (!this.adapterIntances.containsKey(element.getClass())) {
			logger.warn("Could not find an adapter for: " + element.getClass()
					+ " with engine: " + engine.getName());
			return;
		}
		Object adapter = this.adapterIntances.get(element.getClass());
		Set<Method> methods = this.preSearchMethods.get(adapter);
		if (methods.size() > 0) {
			ArrayList<Object> arguments = new ArrayList<Object>(Arrays.asList(objects));
			arguments.add(element);
			this.doAdapt(adapter, methods, arguments);		}
	}

	public void doPostSearchAdapt(Collection<SearchElement> elements,
			SearchEngine<?, ?> engine, Object... objects) {
		for (SearchElement element : elements) {
			doPostSearchAdapt(element, engine, objects);
		}
	}

	public void doPostSearchAdapt(SearchElement element,
			SearchEngine<?, ?> engine, Object... objects) {
		if (!this.adapterIntances.containsKey(element.getClass())) {
			logger.warn("Could not find an adapter for: " + element.getClass()
					+ " with engine: " + engine.getName());
			return;
		}
		Object adapter = this.adapterIntances.get(element.getClass());
		Set<Method> methods = this.postSearchMethods.get(adapter);
		if (methods.size() > 0) {
			ArrayList<Object> arguments = new ArrayList<Object>(Arrays.asList(objects));
			arguments.add(element);
			this.doAdapt(adapter, methods, arguments);
		}
	}

	private Map<Class<?>, List<Object>> mapArguments(Map<Class<?>, List<Object>> map,
			List<Object> objects) {
		if (objects.size() == 0) {
			return map;
		} else {
			Object currentObject = objects.remove(0);
			if (Collection.class.isAssignableFrom(currentObject.getClass())) {
				//objects.addAll((Collection<? extends Object>) currentObject);
			}
			if (!map.containsKey(currentObject.getClass())) {
				map.put(currentObject.getClass(), new ArrayList<Object>());
			}
			map.get(currentObject.getClass()).add(currentObject);

			return mapArguments(map, objects);
		}
	}

	private List<Method> matchingdMethods(Set<Method> methods,
			Set<Class<?>> classSet) {
		List<Method> matchingMethods = new ArrayList<Method>();
		for (Method method : methods) {
			int match = 0;
			Class<?>[] paramTypes = method.getParameterTypes();
			for (Class<?> paramType : paramTypes) {
				for(Class<?> clazz:classSet){
					if (paramType.isAssignableFrom(clazz)) {
						match++;
					}					
				}
			}
			if (match == paramTypes.length) {
				matchingMethods.add(method);
			}
		}
		return matchingMethods;
	}

	private void executeMethod(Object caller, Method method, Object... arguments) {
		try {
			method.setAccessible(true);
			method.invoke(caller, arguments);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.error("Could not invoke method " + method.getName()
					+ " on: " + caller.getClass().getSimpleName(), e);
		}
	}
	
	/** Permute all possible parameters
	 * 
	 * @param caller
	 * @param method
	 * @param allArguments
	 * @param offset
	 * @param arguments
	 */
	private void executeMethodWithPermutation(Object caller, Method method, Object[][] allArguments, int offset, Object[] arguments) {
		if(offset<arguments.length){
			for(int i = 0; i<allArguments[offset].length; i++){
				arguments[offset] = allArguments[offset][i];
				executeMethodWithPermutation(caller, method, allArguments, offset+1,arguments);	
			}
		} else {
			logger.debug("Found a working permutation for method: " + method.getName());
			String out = "\tPermutation is: ";
			for(Object obj:arguments){
				out += obj.getClass().getSimpleName()+", ";
			}
			logger.debug(out);
			executeMethod(caller, method, arguments);
		}
	}

	private void doAdapt(Object adapter, Set<Method> methods, List<Object> objects) {
		
//		for(Object obj:objects){
//			logger.debug("This is my object: " + obj.getClass());
//		}

		// map the list of arguments to their class definition
		Map<Class<?>, List<Object>> arguments = mapArguments(new HashMap<Class<?>, List<Object>>(), objects);
		
		
		for(Class<?> clazz:arguments.keySet()){
			logger.debug("Bag for class: " + clazz.getName());
			for(Object obj:arguments.get(clazz)){
				logger.debug("\tin bag: " + obj.getClass());
			}
		}

		for (Method method : matchingdMethods(methods, arguments.keySet())) {
			Class<?>[] paramTypes = method.getParameterTypes();
			Object[][] parameters = new Object[paramTypes.length][];
			
			logger.debug("Got a matching method: " + method.getName());
			int x = 0;
			for(Class<?> paramType:paramTypes){
				
				logger.debug("Checking for parameter of param: " + (x)  + " as " + paramType.getSimpleName());
				List<Object> currentparamters = new ArrayList<Object>();
				for(Entry<Class<?>, List<Object>> entry:arguments.entrySet()){
					logger.debug("\t is " + paramType.isAssignableFrom(entry.getKey()) + " class: " +  entry.getKey().getSimpleName() + " should be list: " +entry.getValue().getClass());
					if(paramType.isAssignableFrom(entry.getKey())){
						for(Object goodparam:entry.getValue()){
							currentparamters.add(goodparam);
						}
					}
				}
				parameters[x] = currentparamters.toArray(new Object[0]);
				x++;
			}
			
			logger.debug("We'll need " + paramTypes.length + " params");
			logger.debug("Method bag is: ");
			for(int i=0; i<paramTypes.length; i++){
				logger.debug("Bag for param: " + paramTypes[i].getSimpleName() + " is this bag a list? " + parameters[i].getClass().getSimpleName());
				for(Object obj:parameters[i]){
					logger.debug("\tin bag: " + obj.getClass().getSimpleName());
				}
			}
			executeMethodWithPermutation(adapter, method, parameters, 0, new Object[paramTypes.length]);
		}
	}
	
	private static void ploplop( List<List<String>> allArguments, int offset, Object[] arguments) {
		if(offset<allArguments.size()){
			for(int i = 0; i<allArguments.get(offset).size(); i++){
				arguments[offset] = allArguments.get(offset).get(i);
				ploplop( allArguments, offset+1,arguments);	
			}
		} else {
			logger.debug("Permutation is: " + Arrays.toString(arguments));
			//executeMethod(caller, method, arguments);
		}
	}
	
	public static void main(String...args){
		List<List<String>> stuff = new ArrayList<List<String>>();
		List<String> l1 = new ArrayList<String>();
		l1.add("A");
//		l1.add("B");
//		List<String> l2 = new ArrayList<String>();
//		l2.add("-");
//		l2.add("+");
//		List<String> l3 = new ArrayList<String>();
//		l3.add("1");
//		l3.add("2");
//		l3.add("3");
		stuff.add(l1);
//		stuff.add(l2);
//		stuff.add(l3);
		
		ploplop(stuff, 0, new String[1]);
		
	}
}
