package com.searchbox.web.admin;
import com.searchbox.domain.dm.FieldType;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/fieldtypes")
@Controller
@RooWebScaffold(path = "admin/fieldtypes", formBackingObject = FieldType.class)
public class FieldTypeController {
}
