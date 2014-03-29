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
package com.searchbox.framework.web;

import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.searchbox.framework.model.CollectionEntity;
import com.searchbox.framework.model.SearchEngineEntity;
import com.searchbox.framework.model.SearchboxEntity;
import com.searchbox.framework.model.UserEntity;
import com.searchbox.framework.repository.CollectionRepository;
import com.searchbox.framework.repository.SearchEngineRepository;
import com.searchbox.framework.repository.SearchboxRepository;

@Controller
@RequestMapping("/")
public class HomeController {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(HomeController.class);

  @Autowired
  SearchboxRepository searchboxRepository;

  @Autowired
  CollectionRepository collectionRepository;

  @Autowired
  SearchEngineRepository searchEngineRepository;
  
  @Autowired
  ServletContext servletContext;
  
  @ModelAttribute("context")
  public ServletContext getServletContext(){
    
    return servletContext;
  }
  
  @ModelAttribute("request")
  public HttpServletRequest getServletRequest(HttpServletRequest request){
    return request;
  }

  @ModelAttribute("collections")
  public List<CollectionEntity<?>> getAllCollections() {
    ArrayList<CollectionEntity<?>> list = new ArrayList<CollectionEntity<?>>();
    Iterator<CollectionEntity<?>> it = collectionRepository.findAll()
        .iterator();
    while (it.hasNext()) {
      list.add(it.next());
    }
    return list;
  }

  @ModelAttribute("searchengines")
  public List<SearchEngineEntity<?>> getAllSearchEngines() {
    ArrayList<SearchEngineEntity<?>> list = new ArrayList<SearchEngineEntity<?>>();
    Iterator<SearchEngineEntity<?>> it = searchEngineRepository.findAll()
        .iterator();
    while (it.hasNext()) {
      list.add(it.next());
    }
    return list;
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
  
  @ModelAttribute("user")
  public UserEntity getUser(@AuthenticationPrincipal UserEntity user){
    return user;
  }

  @RequestMapping()
  public ModelAndView home(@AuthenticationPrincipal UserEntity user,
      HttpServletRequest request, ModelAndView model,
      RedirectAttributes redirectAttributes) {

    ModelAndView mav = new ModelAndView("index");
    return mav;
  }
  
  @RequestMapping("/auth/openid")
  public ModelAndView home(@RequestParam(required=true) String url, 
      HttpServletRequest request, ModelAndView model){
    model.setViewName("util/autologin");
    model.addObject("openid_identifier", url);
    return model;
  }
}
