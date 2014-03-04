package com.searchbox.framework.web.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.searchbox.framework.domain.CollectionDefinition;
import com.searchbox.framework.repository.CollectionRepository;

@Controller
@RequestMapping("/{searchbox}/admin/CollectionDefinition")
public class CollectionDefinitionController {
	
	private static Logger logger = LoggerFactory.getLogger(CollectionDefinitionController.class);
		
	@Autowired
	CollectionRepository repository;

	@RequestMapping(value = "/{id}")
    public ModelAndView show(@PathVariable("id") Long id) {
		logger.info("VIEW an filed element");
		CollectionDefinition collectiondef =  repository.findOne(id);
		ModelAndView model = new ModelAndView("admin/CollectionDefinition/updateForm");
        model.addObject("collectionDefinition", collectiondef);
        return model;
    }

}
