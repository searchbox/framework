package com.searchbox.web;
import com.searchbox.domain.Field;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/fields")
@Controller
@RooWebScaffold(path = "fields", formBackingObject = Field.class)
public class FieldController {
}
