package com.searchbox.web;
import com.searchbox.domain.app.facet.RangeFacetDefinition;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/rangefacetdefinitions")
@Controller
@RooWebScaffold(path = "rangefacetdefinitions", formBackingObject = RangeFacetDefinition.class)
public class RangeFacetDefinitionController {
}
