package com.searchbox.framework.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.core.search.SearchElement;
import com.searchbox.framework.domain.FieldAttributeDefinition;
import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.domain.SearchElementDefinition;
import com.searchbox.framework.domain.Searchbox;
import com.searchbox.framework.repository.FieldAttributeRepository;
import com.searchbox.framework.repository.PresetRepository;
import com.searchbox.framework.repository.SearchElementRepository;
import com.searchbox.framework.repository.SearchEngineRepository;
import com.searchbox.framework.service.DirectoryService;
import com.searchbox.framework.service.SearchAdapterService;
import com.searchbox.framework.service.SearchEngineService;
import com.searchbox.framework.service.SearchService;

//@Controller
//@RequestMapping("/asynch/{searchbox}")
public class ASynchController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ASynchController.class);

    @Autowired
    ApplicationConversionService applicationConversionService;

    @Autowired
    ConversionService conversionService;

    @Autowired
    private SearchAdapterService adapterService;

    @Autowired
    SearchService searchService;

    @Autowired
    SearchEngineService searchEngineService;

    @Autowired
    DirectoryService directoryService;

    @Autowired
    SearchElementRepository elementRepository;

    @Autowired
    FieldAttributeRepository fieldAttributeRepository;

    @Autowired
    protected PresetRepository presetRepository;

    @Autowired
    protected SearchEngineRepository searchEngineRepository;

    public ASynchController() {
    }

    @RequestMapping(value = { "/{preset}/{id}", "/{preset}/{id}/" })
    @ResponseBody
    public Map<String, Object> getDefaultPreset(
            @PathVariable Searchbox searchbox, @PathVariable Long id,
            @PathVariable PresetDefinition preset, HttpServletRequest request,
            ModelAndView model, RedirectAttributes redirectAttributes) {

        // Fetch all search Conditions within HTTP params
        Set<AbstractSearchCondition> conditions = new HashSet<AbstractSearchCondition>();
        for (String param : applicationConversionService
                .getSearchConditionParams()) {
            if (request.getParameterValues(param) != null) {
                for (String value : request.getParameterValues(param)) {
                    if (value != null && !value.isEmpty()) {
                        try {
                            AbstractSearchCondition cond = (AbstractSearchCondition) conversionService
                                    .convert(
                                            value,
                                            applicationConversionService
                                                    .getSearchConditionClass(param));
                            conditions.add(cond);
                        } catch (Exception e) {
                            LOGGER.error("Could not convert " + value, e);
                        }
                    }
                }
            }
        }

        LOGGER.debug("Assynch conditions: {}", conditions);

        SearchElementDefinition elementDefinition = elementRepository
                .findOne(id);
        SearchElement element = elementDefinition.getInstance();

        Set<FieldAttribute> fieldAttributes = new HashSet<FieldAttribute>();
        for (FieldAttributeDefinition def : preset.getFieldAttributes()) {
            fieldAttributes.add(def.getInstance());
        }

        SearchEngine<?, ?> searchEngine = preset.getCollection()
                .getSearchEngine().getInstance();
        searchEngine.setCollection(preset.getCollection().getInstance());

        Map<String, Object> results = new HashMap<String, Object>();

        adapterService.doAdapt(SearchAdapter.Time.ASYNCH, null, searchEngine,
                searchEngine.newQuery(), results, fieldAttributes, element,
                conditions);

        return results;
    }

}
