/*******************************************************************************
 * Copyright Searchbox - http://www.searchbox.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.ref.ReflectionUtils;

@Service
public class SearchAdapterService implements InitializingBean{

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SearchAdapterService.class);

	@Autowired
	ApplicationContext context;

	private Map<SearchAdapter.Time,  Map<Method, Object>> searchAdapterMethods;

	public SearchAdapterService() {
		this.searchAdapterMethods = new HashMap<SearchAdapter.Time,  Map<Method, Object>>();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	
		// Reset maps for adapters.
		this.searchAdapterMethods = new HashMap<SearchAdapter.Time,  Map<Method, Object>>();


		// Scan the classpath for adapters
		for (Entry<String, Object> bean : context.getBeansWithAnnotation(
				SearchAdapter.class).entrySet()) {
			Object adapter = bean.getValue();
			for (Method method : adapter.getClass().getDeclaredMethods()) {
				SearchAdapter.Time t = method.getAnnotation(SearchAdapterMethod.class).execute();
				LOGGER.debug("Registering adapter " + t + "\t-- " + method.getName());
				this.addSearchAdapterMethod(t, method, adapter);
			}
		}
	}
	

	public void addSearchAdapterMethod(SearchAdapter.Time time, Method method, Object adapter){
		if(!this.searchAdapterMethods.containsKey(time)){
			this.searchAdapterMethods.put(time, new HashMap<Method, Object>());
		}
		this.searchAdapterMethods.get(time).put(method, adapter);
	}

	public void doAdapt(SearchAdapter.Time time,  Class<?> requiredArg, Object... objects){
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.addAll(Arrays.asList(objects));
		this.doAdapt(requiredArg, this.searchAdapterMethods.get(time), arguments);
	}


	private void doAdapt(Class<?> requiredArg, Map<Method, Object> methods, List<Object> objects) {
	
		//Generate Parameter bag
		Map<Class<?>, List<Object>> arguments = mapArguments(new HashMap<Class<?>, List<Object>>(), objects);
		

		LOGGER.trace("XOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXO");
		LOGGER.trace("XOXOXOXOXOXOXOXOXOXOXOXO filter: " + ((requiredArg==null)?"null":requiredArg.getSimpleName()));	
		LOGGER.trace("XOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXO");
		for(Class<?> clazz:arguments.keySet()){
			LOGGER.trace("Bag for class: " + clazz.getSimpleName());
			for(Object obj:arguments.get(clazz)){
				if(Collection.class.isAssignableFrom(obj.getClass())){
					Iterator<?> obji = ((Collection<?>)obj).iterator();
					String out = "\tin bag: ";
					while(obji.hasNext()){
						out += obji.next().getClass().getSimpleName()+", ";
					}
					LOGGER.trace(out);
				} else {
					LOGGER.trace("\tin bag: " + obj.getClass().getSimpleName());
				}
			}
		}
		LOGGER.trace("~~~~~~~~~~~~~~~~~~~~~~~~");


		for (Method method : matchingdMethods(requiredArg, methods.keySet(), arguments.keySet())) {
			Class<?>[] paramTypes = method.getParameterTypes();
			Object[][] parameters = new Object[paramTypes.length][];
			
			//Generate parameter matrix for permutation.
			LOGGER.debug("Got a matching method: " + method.getName());
			int x = 0;
			for(Class<?> paramType:paramTypes){
				
//				LOGGER.debug("Checking for parameter of param: " + (x)  + " as " + paramType.getSimpleName());
				List<Object> currentparamters = new ArrayList<Object>();
				for(Entry<Class<?>, List<Object>> entry:arguments.entrySet()){
//					LOGGER.debug("\t is " + paramType.isAssignableFrom(entry.getKey()) + " class: " +  entry.getKey().getSimpleName() + " should be list: " +entry.getValue().getClass());
					if(paramType.isAssignableFrom(entry.getKey())){
						for(Object goodparam:entry.getValue()){
							currentparamters.add(goodparam);
						}
					}
				}
				parameters[x] = currentparamters.toArray(new Object[0]);
				x++;
			}
			
			LOGGER.debug("We'll need " + paramTypes.length + " params");
			LOGGER.debug("Method bag is: ");
			for(int i=0; i<paramTypes.length; i++){
				LOGGER.debug("Bag for param: " + paramTypes[i].getSimpleName() + " is this bag a list? " + parameters[i].getClass().getSimpleName());
				for(Object obj:parameters[i]){
					LOGGER.debug("\tin bag: " + obj.getClass().getSimpleName()+"\t"+obj.toString());
				}
			}
			
			//Execute method with permutated arguments.
			List<Object[]> argumentBags = ReflectionUtils.findAllArgumentPermutations(parameters);
			for(Object[] argumentsInBag:argumentBags){
				LOGGER.trace("Found a working permutation for method: " + method.getName());
				for(Object obj:argumentsInBag){
					LOGGER.trace("\t"+obj.getClass().getSimpleName()+", "+obj.toString());
				}
				this.executeMethod(methods.get(method), method, argumentsInBag);
			}
			LOGGER.trace("~~~~~~~~~~~~~~~~~~~~~~~~");
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
			LOGGER.trace("~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~+~~+");
			LOGGER.trace("Mathing Method: " + method.getName());
			int match = 0;
			Class<?>[] paramTypes = method.getParameterTypes();
			for (Class<?> paramType : paramTypes) {
				for(Class<?> clazz:classSet){
					LOGGER.trace("\tclass: " + clazz.getSimpleName() +
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
			LOGGER.trace("Mathing for method is: " + (hasRequiredClass && (match == paramTypes.length)));
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
			LOGGER.error("Method name: "+ method.getName());
			for(Object object:arguments){
				LOGGER.error("\t Param: " + object.getClass().getSimpleName()+"\t"+object.toString());
			}
			LOGGER.error("Could not invoke method " + method.getName()
					+ " on: " + caller.getClass().getSimpleName(), e);
		}
	}
}
