package com.searchbox.web;
import com.searchbox.domain.collection.SolrCloudCollection;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/solrcloudcollections")
@Controller
@RooWebScaffold(path = "solrcloudcollections", formBackingObject = SolrCloudCollection.class)
public class SolrCloudCollectionController {
}
