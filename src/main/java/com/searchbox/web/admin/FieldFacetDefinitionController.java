package com.searchbox.web.admin;
import com.searchbox.domain.app.facet.FieldFacetDefinition;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/fieldfacetdefinitions")
@Controller
@RooWebScaffold(path = "admin/fieldfacetdefinitions", formBackingObject = FieldFacetDefinition.class)
public class FieldFacetDefinitionController {
}
