package com.searchbox.framework.web.admin;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.domain.Searchbox;
import com.searchbox.framework.web.SearchController;

@Controller
@RequestMapping("/{searchbox}/admin/")
public class AdminController extends SearchController {

	

	@ModelAttribute("OrderEnum")
	public List<Order> getReferenceOrder() {
		return Arrays.asList(Order.values());
	}

	@ModelAttribute("SortEnum")
	public List<Sort> getReferenceSort() {
		return Arrays.asList(Sort.values());
	}
	
	protected String getView() {
		return "admin/search";
	}
	
	@RequestMapping()
	public ModelAndView search(
			HttpServletRequest request, ModelAndView model,
			RedirectAttributes redirectAttributes) {

		ModelAndView mav = new ModelAndView("admin/index");
		return mav;
	}

	
	@Override
	@RequestMapping("/{preset}")
	public ModelAndView executeSearch(
			@ModelAttribute("searchboxes") List<Searchbox> searchboxes,
			@PathVariable Searchbox searchbox,
			@PathVariable PresetDefinition preset,
			HttpServletRequest request, ModelAndView model,
			RedirectAttributes redirectAttributes) {
		
		model.addObject("presetDefinition", preset);
		return super.executeSearch(searchboxes, searchbox, preset, request, model, redirectAttributes);
	}

}
