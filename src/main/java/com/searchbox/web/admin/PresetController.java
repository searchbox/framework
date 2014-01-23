package com.searchbox.web.admin;
import com.searchbox.domain.app.Preset;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/presets")
@Controller
@RooWebScaffold(path = "admin/presets", formBackingObject = Preset.class)
public class PresetController {
}
