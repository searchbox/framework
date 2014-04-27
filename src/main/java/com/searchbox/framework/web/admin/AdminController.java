/*******************************************************************************
 * Copyright Searchbox - http://www.searchbox.com
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

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.framework.model.CollectionEntity;
import com.searchbox.framework.model.SearchboxEntity;
import com.searchbox.framework.model.UserEntity;
import com.searchbox.framework.service.CollectionService;
import com.searchbox.framework.service.UserService;
import com.searchbox.framework.web.SearchboxController;

@Controller
@RequestMapping("/admin/{searchbox}")
public class AdminController extends SearchboxController {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(AdminController.class);

  @Autowired
  JobExplorer jobExplorer;

  @Autowired
  CollectionService collectionService;

  @Autowired
  UserService userService;


  @ModelAttribute("user")
  public UserEntity getUser(@AuthenticationPrincipal UserEntity user){
    return user;
  }

  @ModelAttribute("users")
  public List<UserEntity> getUsers(@Qualifier("userTable") Pageable page,
      ModelAndView model){
    model.addObject("userTable", page);
    model.addObject("userCount", userService.countAll());
    LOGGER.info("Getting page {} of user", page.getPageNumber());
    return userService.findAll(page);
  }

  @ModelAttribute("collections")
  public List<CollectionEntity<?>> getAllCollections(@PathVariable SearchboxEntity searchbox,
      @RequestParam(required=false, defaultValue="0") int collectionPage) {
    return collectionService.findAll(collectionPage);
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
  protected String getViewFolder() {
    return "admin";
  }

  @RequestMapping(value = { "", "/" })
  @ResponseBody
  public ModelAndView getHome(@PathVariable SearchboxEntity searchbox,
      HttpServletRequest request, ModelAndView model,
      RedirectAttributes redirectAttributes) {
    model.setViewName(this.getViewFolder() + "/index");

    if(searchbox == null){
        LOGGER.error("Searchbox {} not found!",searchbox);
        return new ModelAndView(new RedirectView("/", true));
    }

    return model;
  }

}
