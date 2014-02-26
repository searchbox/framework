package com.searchbox.framework.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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

import com.searchbox.core.PostSearchAdapter;
import com.searchbox.core.PreSearchAdapter;
import com.searchbox.core.SearchAdapter;
import com.searchbox.core.engine.SearchEngine;

@Service
public class SearchAdapterService implements
		ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LoggerFactory
			.getLogger(SearchAdapterService.class);

	@Autowired
	ApplicationContext context;

	private Map<Method, Object> preSearchMethods;
	private Map<Method, Object> postSearchMethods;

	public SearchAdapterService() {
		this.preSearchMethods = new HashMap<Method, Object>();
		this.postSearchMethods = new HashMap<Method, Object>();
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		// Reset maps for adapters.
		this.preSearchMethods = new HashMap<Method, Object>();
		this.postSearchMethods = new HashMap<Method, Object>();

		// Scan the classpath for adapters
		for (Entry<String, Object> bean : context.getBeansWithAnnotation(
				SearchAdapter.class).entrySet()) {
			Object adapter = bean.getValue();
			for (Method method : adapter.getClass().getDeclaredMethods()) {
				if (method.isAnnotationPresent(PreSearchAdapter.class)) {
					logger.debug("Registering Pre adapt: "
							+ method.getName());
					this.preSearchMethods.put(method,adapter);
				} else if(method.isAnnotationPresent(PostSearchAdapter.class)) {
					logger.debug("Registering Post adapt: "
							+ method.getName());
					this.postSearchMethods.put(method,adapter);
				}
			}
		}
	}

	public void doPreSearchAdapt(SearchEngine<?, ?> engine, Class<?> requiredArg, Object... objects) {
		//This is because "Arrays.asList(objects)" does not support remove;
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.addAll(Arrays.asList(objects));
		this.doAdapt(requiredArg, this.preSearchMethods, arguments);
	}

	public void doPostSearchAdapt(SearchEngine<?, ?> engine, Class<?> requiredArg, Object... objects) {
		//This is because "Arrays.asList(objects)" does not support remove;
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.addAll(Arrays.asList(objects));
		this.doAdapt(requiredArg, this.postSearchMethods, arguments);
	}


	private void doAdapt(Class<?> requiredArg, Map<Method, Object> methods, List<Object> objects) {
	
		//Generate Parameter bag
		Map<Class<?>, List<Object>> arguments = mapArguments(new HashMap<Class<?>, List<Object>>(), objects);
		

		logger.trace("XOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXO");
		logger.trace("XOXOXOXOXOXOXOXOXOXOXOXO filter: " + ((requiredArg==null)?"null":requiredArg.getSimpleName()));	
		logger.trace("XOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXO");
		for(Class<?> clazz:arguments.keySet()){
			logger.trace("Bag for class: " + clazz.getSimpleName());
			for(Object obj:arguments.get(clazz)){
				if(Collection.class.isAssignableFrom(obj.getClass())){
					Iterator<?> obji = ((Collection<?>)obj).iterator();
					String out = "\tin bag: ";
					while(obji.hasNext()){
						out += obji.next().getClass().getSimpleName()+", ";
					}
					logger.trace(out);
				} else {
					logger.trace("\tin bag: " + obj.getClass().getSimpleName());
				}
			}
		}
		logger.trace("~~~~~~~~~~~~~~~~~~~~~~~~");


		for (Method method : matchingdMethods(requiredArg, methods.keySet(), arguments.keySet())) {
			Class<?>[] paramTypes = method.getParameterTypes();
			Object[][] parameters = new Object[paramTypes.length][];
			
			//Generate parameter matrix for permutation.
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
			
			//Execute method with permutated arguments.
			List<Object[]> argumentBags = findAllArgumentPermutations(parameters, 0,
					new Object[paramTypes.length], new ArrayList<Object[]>());
			for(Object[] argumentsInBag:argumentBags){
				logger.trace("Found a working permutation for method: " + method.getName());
				for(Object obj:argumentsInBag){
					logger.trace("\t"+obj.getClass().getSimpleName()+", "+obj.toString());
				}
				this.executeMethod(methods.get(method), method, argumentsInBag);
			}
			logger.trace("~~~~~~~~~~~~~~~~~~~~~~~~");
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private Map<Class<?>, List<Object>> mapArguments(Map<Class<?>, List<Object>> map,
			List<Object> objects) {
		if (objects.size() == 0) {
			return map;
		} else {
			Object currentObject = objects.remove(0);
			if(currentObject!=null){
				if (Collection.class.isAssignableFrom(currentObject.getClass())) {
					objects.addAll((Collection<? extends Object>) currentObject);
				} else {
					if (!map.containsKey(currentObject.getClass())) {
						map.put(currentObject.getClass(), new ArrayList<Object>());
					}
					map.get(currentObject.getClass()).add(currentObject);
				}
			}
			return mapArguments(map, objects);
		}
	}

	private List<Method> matchingdMethods(Class<?> requiredClass, Set<Method> methods,
			Set<Class<?>> classSet) {
		List<Method> matchingMethods = new ArrayList<Method>();
		boolean hasRequiredClass = (requiredClass == null)?true:false;
		for (Method method : methods) {
			logger.trace("~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~~+");
			logger.trace("Mathing Method: " + method.getName());
			int match = 0;
			Class<?>[] paramTypes = method.getParameterTypes();
			for (Class<?> paramType : paramTypes) {
				for(Class<?> clazz:classSet){
					logger.trace("\tclass: " + clazz.getSimpleName() +
							"\tparamType: " + paramType.getSimpleName() +
							"\t" + paramType.isAssignableFrom(clazz) +
							((requiredClass==null)?"":"\trequired:"+requiredClass.isAssignableFrom(paramType)));
					if (paramType.isAssignableFrom(clazz)) {
						match++;
						if(requiredClass != null && requiredClass.isAssignableFrom(paramType)){
							hasRequiredClass = true;
						}
						break;
					}
					
				}
			}
			logger.trace("Mathing for method is: " + (hasRequiredClass && (match == paramTypes.length)));
			if(hasRequiredClass && (match == paramTypes.length)) {
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
			logger.error("Method name: "+ method.getName());
			for(Object object:arguments){
				logger.error("\t Param: " + object.getClass().getSimpleName()+"\t"+object.toString());
			}
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
	private List<Object[]> findAllArgumentPermutations(Object[][] allArguments, int offset, Object[] arguments, List<Object[]> results) {
		if(offset<arguments.length){
			for(int i = 0; i<allArguments[offset].length; i++){
				arguments[offset] = allArguments[offset][i];
				findAllArgumentPermutations(allArguments, offset+1,arguments, results);
			}
			return results;
		} else {
			results.add(arguments);
			return results;
		}
	}
}
