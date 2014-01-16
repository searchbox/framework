package com.searchbox.web;
import com.searchbox.domain.facet.FieldFacet;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/fieldfacets")
@Controller
@RooWebScaffold(path = "fieldfacets", formBackingObject = FieldFacet.class)
public class FieldFacetController {
}
