package com.searchbox.framework.web.admin;

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

import com.searchbox.framework.domain.CollectionDefinition;
import com.searchbox.framework.repository.CollectionRepository;
import com.searchbox.framework.service.CollectionService;

@Controller
@RequestMapping("/{searchbox}/admin/CollectionDefinition")
public class CollectionDefinitionController {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectionDefinitionController.class);
		
	@Autowired
	CollectionRepository repository;
	
	@Autowired
	JobExplorer jobExplorer;
	
	@Autowired
	CollectionService service;

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
    public Map<String, String> synchronizeData(@PathVariable("id") Long id) {
		CollectionDefinition collectiondef =  repository.findOne(id);
		return service.synchronizeData(collectiondef);
    }
	
	@RequestMapping(value = {"/{id}/merge","/{id}/merge/"}, method=RequestMethod.POST)
	@ResponseBody
    public Map<String, String> synchronizeDm(@PathVariable("id") Long id) {
		CollectionDefinition collectiondef =  repository.findOne(id);
		return service.synchronizeDm(collectiondef);
    }

}
