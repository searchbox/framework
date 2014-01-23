package com.searchbox.web.admin;
import com.searchbox.domain.search.facet.FieldFacet;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/fieldfacets")
@Controller
@RooWebScaffold(path = "admin/fieldfacets", formBackingObject = FieldFacet.class)
public class FieldFacetController {
}
