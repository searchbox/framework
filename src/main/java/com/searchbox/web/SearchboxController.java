package com.searchbox.web;
import com.searchbox.domain.app.Searchbox;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/searchboxes")
@Controller
@RooWebScaffold(path = "searchboxes", formBackingObject = Searchbox.class)
public class SearchboxController {
}
