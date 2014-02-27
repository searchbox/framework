package com.searchbox.framework.web.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.framework.domain.Searchbox;
import com.searchbox.framework.domain.User;
import com.searchbox.framework.domain.UserRole;
import com.searchbox.framework.repository.SearchboxRepository;
import com.searchbox.framework.repository.UserRepository;
import com.searchbox.framework.repository.UserRoleRepository;

@Controller
@RequestMapping("/{searchbox}/admin")
public class AdminController {

	@Autowired
	SearchboxRepository searchboxRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@ModelAttribute("OrderEnum")
	public List<Order> getReferenceOrder() {
		return Arrays.asList(Order.values());
	}

	@ModelAttribute("SortEnum")
	public List<Sort> getReferenceSort() {
		return Arrays.asList(Sort.values());
	}
	
	@ModelAttribute("searchboxes")
	public List<Searchbox> getAllSearchboxes() {
		ArrayList<Searchbox> searchboxes = new ArrayList<Searchbox>();
		Iterator<Searchbox> sbx = searchboxRepository.findAll().iterator();
		while (sbx.hasNext()) {
			searchboxes.add(sbx.next());
		}
		return searchboxes;
	}
	
	@ModelAttribute("users")
	public List<User> getAllUsers() {
		ArrayList<User> list = new ArrayList<User>();
		Iterator<User> it = userRepository.findAll().iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}	
	
	@ModelAttribute("userRoles")
	public List<UserRole> getAllUserRoles() {
		ArrayList<UserRole> list = new ArrayList<UserRole>();
		Iterator<UserRole> it = userRoleRepository.findAll().iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}	

	
	@ModelAttribute("searchbox")
	public Searchbox getSearchbox(@PathVariable Searchbox searchbox){
		return searchbox;
	}

	
	@RequestMapping(value={"/",""})
	public ModelAndView search(
			HttpServletRequest request, ModelAndView model,
			RedirectAttributes redirectAttributes) {

		ModelAndView mav = new ModelAndView("admin/index");
		return mav;
	}
}
