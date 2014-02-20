package com.searchbox.web.admin;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.searchbox.domain.DefinitionAttribute;
import com.searchbox.domain.PresetDefinition;
import com.searchbox.domain.SearchElementDefinition;
import com.searchbox.ref.Order;
import com.searchbox.ref.Sort;

@Controller
@RequestMapping("/admin/searchElementDefinition")
public class SearchElementDefinitionController {

	private static Logger logger = LoggerFactory.getLogger(SearchElementDefinitionController.class);
	
	@Autowired
	ConversionService conversionService;
	
	@ModelAttribute("OrderEnum")
    public List<Order> getReferenceOrder(){
        return Arrays.asList(Order.values());
    }
	
	@ModelAttribute("SortEnum")
    public List<Sort> getReferenceSort(){
        return Arrays.asList(Sort.values());
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
        if(elementDefinition.getId() != null){
        	elementDefinition = elementDefinition.merge();
        } else {
        	elementDefinition.persist();
        }
		} catch (Exception e){
			e.printStackTrace();
		}
        populateEditForm(model, elementDefinition);
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
		SearchElementDefinition elementDef =  SearchElementDefinition.findSearchElementDefinition(id);
		
		ModelAndView model = new ModelAndView("admin/SearchElementDefinition/updateForm");
        populateEditForm(model, elementDef);
        return model;
    }

	@RequestMapping()
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("searchelementdefinitions", SearchElementDefinition.findSearchElementDefinitionEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) SearchElementDefinition.countSearchElementDefinitions() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("searchelementdefinitions", SearchElementDefinition.findAllSearchElementDefinitions(sortFieldName, sortOrder));
        }
        return "/admin/searchElementDefinition/list";
    }

	@RequestMapping(value = "/{id}", params = "form")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        //populateEditForm(uiModel, SearchElementDefinition.findSearchElementDefinition(id));
        return "/admin/searchElementDefinition/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        SearchElementDefinition searchElementDefinition = SearchElementDefinition.findSearchElementDefinition(id);
        searchElementDefinition.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect://admin/searchElementDefinition";
    }

	void populateEditForm(ModelAndView uiModel, SearchElementDefinition searchElementDefinition) {
        uiModel.addObject("searchElementDefinition", searchElementDefinition);
        uiModel.addObject("definitionattributes", DefinitionAttribute.findAllDefinitionAttributes());
        uiModel.addObject("presetdefinitions", PresetDefinition.findAllPresetDefinitions());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
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
