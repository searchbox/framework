package com.searchbox.web;
import com.searchbox.domain.dm.FieldType;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/fieldtypes")
@Controller
@RooWebScaffold(path = "fieldtypes", formBackingObject = FieldType.class)
public class FieldTypeController {
}
