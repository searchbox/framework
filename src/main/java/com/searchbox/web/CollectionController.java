package com.searchbox.web;
import com.searchbox.domain.dm.Collection;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/collections")
@Controller
@RooWebScaffold(path = "collections", formBackingObject = Collection.class)
public class CollectionController {
}
