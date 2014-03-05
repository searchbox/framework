package com.searchbox.framework.web.admin;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.searchbox.collection.SynchronizedCollection;
import com.searchbox.framework.domain.CollectionDefinition;
import com.searchbox.framework.repository.CollectionRepository;

@Controller
@RequestMapping("/{searchbox}/admin/CollectionDefinition")
public class CollectionDefinitionController {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectionDefinitionController.class);
		
	@Autowired
	CollectionRepository repository;
	
	@Autowired
	JobExplorer jobExplorer;

	@RequestMapping(value = "/{id}")
    public ModelAndView show(@PathVariable("id") Long id) {
		CollectionDefinition collectiondef =  repository.findOne(id);
		ModelAndView model = new ModelAndView("admin/CollectionDefinition/updateForm");
        model.addObject("collectionDefinition", collectiondef);
        model.addObject("jobExplorer",jobExplorer);

        return model;
    }
	
	@RequestMapping(value = {"/{id}/synchronize","/{id}/synchronize/"}, method=RequestMethod.POST)
	@ResponseBody
    public Map<String, String> synchronize(@PathVariable("id") Long id) {
		Map<String, String> result = new HashMap<String, String>();
		CollectionDefinition collectiondef =  repository.findOne(id);
		com.searchbox.core.dm.Collection collection = collectiondef.getInstance();
		if(SynchronizedCollection.class.isAssignableFrom(collection.getClass())){
			try {
				((SynchronizedCollection)collection).synchronize();
			} catch (Exception e){
				result.put("status","KO");
				result.put("message",e.getMessage());
				return result;
			}
			result.put("status","OK");
			result.put("message","Synchronization started");
		} else {
			result.put("status","KO");
			result.put("message","Collection is not synchronizable");
		}
		return result;
    }

}
