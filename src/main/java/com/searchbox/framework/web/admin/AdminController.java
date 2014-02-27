package com.searchbox.framework.web.admin;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;

@Controller
@RequestMapping("/{searchbox}/admin")
public class AdminController {

	

	@ModelAttribute("OrderEnum")
	public List<Order> getReferenceOrder() {
		return Arrays.asList(Order.values());
	}

	@ModelAttribute("SortEnum")
	public List<Sort> getReferenceSort() {
		return Arrays.asList(Sort.values());
	}
	
	@RequestMapping(value={"/",""})
	public ModelAndView search(
			HttpServletRequest request, ModelAndView model,
			RedirectAttributes redirectAttributes) {

		ModelAndView mav = new ModelAndView("admin/index");
		return mav;
	}
}
