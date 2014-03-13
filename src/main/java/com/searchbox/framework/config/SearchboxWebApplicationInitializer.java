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
package com.searchbox.framework.config;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SearchboxWebApplicationInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SearchboxWebApplicationInitializer.class);

    private static double JAVA_VERSION = getVersion();

    public SearchboxWebApplicationInitializer() {
        super();

        if (JAVA_VERSION < 1.7) {
            LOGGER.error("Java 7 is required to run Searchbox. Current version: "
                    + JAVA_VERSION + ". Please update your system.");
            // throw new Exception("bad value");
            System.exit(0);
        }
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfiguration.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] { new HiddenHttpMethodFilter(),
                new OpenEntityManagerInViewFilter() };
    }

    /**
     * Retrieving current java version
     * 
     * @return Double Java version
     */
    private static double getVersion() {
        String version = System.getProperty("java.version");
        LOGGER.info("Checking java version. Version found :" + version);
        int pos = 0, count = 0;
        for (; pos < version.length() && count < 2; pos++) {
            if (version.charAt(pos) == '.') {
                count++;
            }
        }
        --pos; // EVALUATE double
        double dversion = Double.parseDouble(version.substring(0, pos));
        return dversion;
    }

}
