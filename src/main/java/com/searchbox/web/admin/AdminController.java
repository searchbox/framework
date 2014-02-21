package com.searchbox.web.admin;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.searchbox.ref.Order;
import com.searchbox.ref.Sort;
import com.searchbox.web.SearchController;

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
}
