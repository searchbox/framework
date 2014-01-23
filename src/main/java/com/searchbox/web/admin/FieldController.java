package com.searchbox.web.admin;
import com.searchbox.domain.dm.Field;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/fields")
@Controller
@RooWebScaffold(path = "admin/fields", formBackingObject = Field.class)
public class FieldController {
}
