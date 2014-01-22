package com.searchbox.web;
import com.searchbox.domain.app.facet.FieldFacetDefinition;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/fieldfacetdefinitions")
@Controller
@RooWebScaffold(path = "fieldfacetdefinitions", formBackingObject = FieldFacetDefinition.class)
public class FieldFacetDefinitionController {
}
