package com.searchbox.framework.web.admin;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.domain.Searchbox;
import com.searchbox.framework.web.SearchController;

@Controller
@RequestMapping("/{searchbox}/admin/search")
public class PresetController extends SearchController{
	
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
	
	protected String getSearchUrl(Searchbox searchbox, PresetDefinition preset) {
		return "/"+searchbox.getSlug()+"/admin/search/"+preset.getSlug();
	}

}
