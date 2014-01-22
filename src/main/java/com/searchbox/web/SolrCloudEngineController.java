package com.searchbox.web;
import com.searchbox.domain.engine.SolrCloudEngine;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/solrcloudengines")
@Controller
@RooWebScaffold(path = "solrcloudengines", formBackingObject = SolrCloudEngine.class)
public class SolrCloudEngineController {
}
