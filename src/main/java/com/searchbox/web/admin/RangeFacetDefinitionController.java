package com.searchbox.web.admin;
import com.searchbox.domain.app.facet.RangeFacetDefinition;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/rangefacetdefinitions")
@Controller
@RooWebScaffold(path = "admin/rangefacetdefinitions", formBackingObject = RangeFacetDefinition.class)
public class RangeFacetDefinitionController {
}
