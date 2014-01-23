package com.searchbox.web.admin;
import com.searchbox.domain.app.FieldDefinition;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/fielddefinitions")
@Controller
@RooWebScaffold(path = "admin/fielddefinitions", formBackingObject = FieldDefinition.class)
public class FieldDefinitionController {
}
