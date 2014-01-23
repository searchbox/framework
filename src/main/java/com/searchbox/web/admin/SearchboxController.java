package com.searchbox.web.admin;
import com.searchbox.domain.app.Searchbox;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/searchboxes")
@Controller
@RooWebScaffold(path = "admin/searchboxes", formBackingObject = Searchbox.class)
public class SearchboxController {
}
