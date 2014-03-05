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

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.domain.Searchbox;
import com.searchbox.framework.web.SearchController;

@Controller
@RequestMapping("/{searchbox}/admin")
public class AdminSearchController extends SearchController {

	@Autowired
	JobExplorer jobExplorer;
	
	@ModelAttribute("OrderEnum")
	public List<Order> getReferenceOrder() {
		return Arrays.asList(Order.values());
	}

	@ModelAttribute("SortEnum")
	public List<Sort> getReferenceSort() {
		return Arrays.asList(Sort.values());
	}
	
	@ModelAttribute("jobExplorer")
	public JobExplorer getJobExplorer() {
		return this.jobExplorer;
	}

	@Override
	protected String getViewViewName() {
		return "admin/view";
	}
	
	@Override
	protected String getSearchViewName() {
		return "admin/search";
	}
	
	@Override
	protected String getSearchUrl(Searchbox searchbox, PresetDefinition preset) {
		return "/" + searchbox.getSlug() + "/admin/search/" + preset.getSlug();
	}
}
