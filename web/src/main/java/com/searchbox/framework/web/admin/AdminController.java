/*******************************************************************************
 * Copyright SearchboxEntity - http://www.searchbox.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.searchbox.framework.web.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.framework.domain.UserRole;
import com.searchbox.framework.model.SearchboxEntity;
import com.searchbox.framework.model.UserEntity;
import com.searchbox.framework.repository.SearchboxRepository;
import com.searchbox.framework.repository.UserRepository;
import com.searchbox.framework.repository.UserRoleRepository;

//@Controller
//@RequestMapping("/admin/{searchbox}")
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
  public List<SearchboxEntity> getAllSearchboxEntityes() {
    ArrayList<SearchboxEntity> searchboxes = new ArrayList<SearchboxEntity>();
    Iterator<SearchboxEntity> sbx = searchboxRepository.findAll().iterator();
    while (sbx.hasNext()) {
      searchboxes.add(sbx.next());
    }
    return searchboxes;
  }

  @ModelAttribute("users")
  public List<UserEntity> getAllUsers() {
    ArrayList<UserEntity> list = new ArrayList<UserEntity>();
    Iterator<UserEntity> it = userRepository.findAll().iterator();
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
  public SearchboxEntity getSearchboxEntity(
      @PathVariable SearchboxEntity searchbox) {
    return searchbox;
  }

  @RequestMapping(value = { "/", "" })
  public ModelAndView search(HttpServletRequest request, ModelAndView model,
      RedirectAttributes redirectAttributes) {

    ModelAndView mav = new ModelAndView("admin/index");
    return mav;
  }
}
