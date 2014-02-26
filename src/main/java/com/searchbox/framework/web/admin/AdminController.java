package com.searchbox.framework.web.admin;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.domain.Searchbox;
import com.searchbox.framework.web.SearchController;

@Controller
@RequestMapping("/admin")
public class AdminController extends SearchController {

	@Override
	public String getIndexView() {
		return "admin/index";
	}

	@Override
	public String getHomeView() {
		return "admin/home";
	}

	@ModelAttribute("OrderEnum")
	public List<Order> getReferenceOrder() {
		return Arrays.asList(Order.values());
	}

	@ModelAttribute("SortEnum")
	public List<Sort> getReferenceSort() {
		return Arrays.asList(Sort.values());
	}
	
	@Override
	protected ModelAndView executeSearch(Searchbox searchbox,
			PresetDefinition presetDefinition, HttpServletRequest request,
			ModelAndView model) {
		model.addObject("presetDefinition", presetDefinition);
		return super.executeSearch(searchbox, presetDefinition, request, model);
	}

}
