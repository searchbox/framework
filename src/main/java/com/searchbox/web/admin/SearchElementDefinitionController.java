package com.searchbox.web.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.searchbox.app.domain.DefinitionAttribute;
import com.searchbox.app.domain.PresetDefinition;
import com.searchbox.app.domain.SearchElementDefinition;
import com.searchbox.app.repository.PresetDefinitionRepository;
import com.searchbox.app.repository.SearchElementDefinitionRepository;
import com.searchbox.ref.Order;
import com.searchbox.ref.Sort;

@Controller
@RequestMapping("/admin/searchElementDefinition")
public class SearchElementDefinitionController {

	private static Logger logger = LoggerFactory.getLogger(SearchElementDefinitionController.class);
	
	@Autowired
	ConversionService conversionService;
	
	@Autowired
	SearchElementDefinitionRepository repository;
	
	@Autowired
	PresetDefinitionRepository presetRepository;
	
	@ModelAttribute("OrderEnum")
    public List<Order> getReferenceOrder(){
        return Arrays.asList(Order.values());
    }
	
	@ModelAttribute("SortEnum")
    public List<Sort> getReferenceSort(){
        return Arrays.asList(Sort.values());
    }
	
	@ModelAttribute("presetdefinitions")
    public List<PresetDefinition> getPresetDefinitions(){
		ArrayList<PresetDefinition> presetDefinitions = new ArrayList<PresetDefinition>();
		Iterator<PresetDefinition> presets = presetRepository.findAll().iterator();
		while(presets.hasNext()){
			presetDefinitions.add(presets.next());
		}
        return presetDefinitions;
    }
	
	@RequestMapping(method = RequestMethod.POST)
    public ModelAndView update(@Valid SearchElementDefinition elementDefinition, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
		logger.info("Creating an filed element: " + elementDefinition.getClazz().getSimpleName() + 
				" for preset: " + elementDefinition.getPreset().getSlug());
	
		ModelAndView model = new ModelAndView("admin/SearchElementDefinition/updateForm");
		
		if (bindingResult.hasErrors()) {
			logger.error("Bindding has error...");
        	for(ObjectError error:bindingResult.getAllErrors()){
        		logger.error("Error: " + error.getDefaultMessage());
        	}
            return model;
        }
		try {
			elementDefinition = repository.save(elementDefinition);
		} catch (Exception e){
			e.printStackTrace();
		}
        model.addObject("searchElementDefinition", elementDefinition);
        return model;
	}
	
	@RequestMapping(params = "form")
    public String create(Model uiModel) {
		logger.info("Creating EMPTY filed element");
        //populateEditForm(uiModel, new SearchElementDefinition());
        return "/admin/searchElementDefinition/create";
    }

	@RequestMapping(value = "/{id}")
    public ModelAndView show(@PathVariable("id") Long id) {
		logger.info("VIEW an filed element");
		SearchElementDefinition elementDef =  repository.findOne(id);
		ModelAndView model = new ModelAndView("admin/SearchElementDefinition/updateForm");
        model.addObject("searchElementDefinition", elementDef);
        return model;
    }

	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new Validator(){

			@Override
			public boolean supports(Class<?> clazz) {
				return SearchElementDefinition.class.isAssignableFrom(clazz);
			}

			@Override
			public void validate(Object target, Errors errors) {
				SearchElementDefinition element = (SearchElementDefinition)target;
				for(DefinitionAttribute attr:element.getAttributes()){
					if(!attr.getType().getName().equals(attr.getValue().getClass().getName())){
						if(conversionService.canConvert(attr.getValue().getClass(), attr.getType())){
							attr.setValue(conversionService.convert(attr.getValue(), attr.getType()));
						}
					}
				}
			}
		});
	}
}
