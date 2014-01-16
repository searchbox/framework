package com.searchbox.web;
import com.searchbox.domain.collection.SolrCollection;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/solrcollections")
@Controller
@RooWebScaffold(path = "solrcollections", formBackingObject = SolrCollection.class)
public class SolrCollectionController {
}
