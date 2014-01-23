package com.searchbox.web.admin;
import com.searchbox.domain.engine.SolrCloudEngine;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/solrcloudengines")
@Controller
@RooWebScaffold(path = "admin/solrcloudengines", formBackingObject = SolrCloudEngine.class)
public class SolrCloudEngineController {
}
