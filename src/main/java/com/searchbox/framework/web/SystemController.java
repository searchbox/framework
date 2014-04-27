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
@RequestMapping("/system")
public class SystemController {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(SystemController.class);

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

  @ModelAttribute("user")
  public UserEntity getUser(@AuthenticationPrincipal UserEntity user){
    return user;
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

  @ModelAttribute("jvmInfo")
  public static Map<String, Object> getJvmInfo() {
    Map<String, Object> jvm = new HashMap<String, Object>();

    final String javaVersion = System.getProperty("java.specification.version",
        "unknown");
    final String javaVendor = System.getProperty("java.specification.vendor",
        "unknown");
    final String javaName = System.getProperty("java.specification.name",
        "unknown");
    final String jreVersion = System.getProperty("java.version", "unknown");
    final String jreVendor = System.getProperty("java.vendor", "unknown");
    final String vmVersion = System.getProperty("java.vm.version", "unknown");
    final String vmVendor = System.getProperty("java.vm.vendor", "unknown");
    final String vmName = System.getProperty("java.vm.name", "unknown");

    // Summary Info
    jvm.put("version", jreVersion + " " + vmVersion);
    jvm.put("name", jreVendor + " " + vmName);

    // details
    Map<String, Object> java = new HashMap<String, Object>();
    java.put("vendor", javaVendor);
    java.put("name", javaName);
    java.put("version", javaVersion);
    jvm.put("spec", java);
    Map<String, Object> jre = new HashMap<String, Object>();
    jre.put("vendor", jreVendor);
    jre.put("version", jreVersion);
    jvm.put("jre", jre);
    Map<String, Object> vm = new HashMap<String, Object>();
    vm.put("vendor", vmVendor);
    vm.put("name", vmName);
    vm.put("version", vmVersion);
    jvm.put("vm", vm);

    Runtime runtime = Runtime.getRuntime();
    jvm.put("processors", runtime.availableProcessors());

    // not thread safe, but could be thread local
    DecimalFormat df = new DecimalFormat("#.#",
        DecimalFormatSymbols.getInstance(Locale.ROOT));

    Map<String, Object> mem = new HashMap<String, Object>();
    Map<String, Object> raw = new HashMap<String, Object>();
    long free = runtime.freeMemory();
    long max = runtime.maxMemory();
    long total = runtime.totalMemory();
    long used = total - free;
    double percentUsed = ((double) (used) / (double) max) * 100;
    raw.put("free", free);
    mem.put("free", humanReadableUnits(free, df));
    raw.put("total", total);
    mem.put("total", humanReadableUnits(total, df));
    raw.put("max", max);
    mem.put("max", humanReadableUnits(max, df));
    raw.put("used", used);
    mem.put("used",
        humanReadableUnits(used, df) + " (%" + df.format(percentUsed) + ")");
    raw.put("used%", percentUsed);

    mem.put("raw", raw);
    jvm.put("memory", mem);

    // JMX properties -- probably should be moved to a different handler
    Map<String, Object> jmx = new HashMap<String, Object>();
    try {
      RuntimeMXBean mx = ManagementFactory.getRuntimeMXBean();
      jmx.put("bootclasspath", mx.getBootClassPath());
      jmx.put("classpath", mx.getClassPath());

      // the input arguments passed to the Java virtual machine
      // which does not include the arguments to the main method.
      jmx.put("commandLineArgs", mx.getInputArguments());

      jmx.put("startTime", new Date(mx.getStartTime()));
      jmx.put("upTimeMS", mx.getUptime());

    } catch (Exception e) {
      LOGGER.warn("Error getting JMX properties", e);
    }
    jvm.put("jmx", jmx);
    return jvm;
  }

  private static final long ONE_KB = 1024;
  private static final long ONE_MB = ONE_KB * ONE_KB;
  private static final long ONE_GB = ONE_KB * ONE_MB;

  /**
   * Return good default units based on byte size.
   */
  private static String humanReadableUnits(long bytes, DecimalFormat df) {
    String newSizeAndUnits;

    if (bytes / ONE_GB > 0) {
      newSizeAndUnits = String.valueOf(df.format((float) bytes / ONE_GB))
          + " GB";
    } else if (bytes / ONE_MB > 0) {
      newSizeAndUnits = String.valueOf(df.format((float) bytes / ONE_MB))
          + " MB";
    } else if (bytes / ONE_KB > 0) {
      newSizeAndUnits = String.valueOf(df.format((float) bytes / ONE_KB))
          + " KB";
    } else {
      newSizeAndUnits = String.valueOf(bytes) + " bytes";
    }

    return newSizeAndUnits;
  }

  @ModelAttribute("systemInfo")
  private Map<String, Object> getSystemInfo() {
    Map<String, Object> info = new HashMap<String, Object>();
    OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
    info.put("name", os.getName());
    info.put("version", os.getVersion());
    info.put("arch", os.getArch());
    info.put("systemLoadAverage", os.getSystemLoadAverage());

    // com.sun.management.OperatingSystemMXBean
    addGetterIfAvaliable(os, "committedVirtualMemorySize", info);
    addGetterIfAvaliable(os, "freePhysicalMemorySize", info);
    addGetterIfAvaliable(os, "freeSwapSpaceSize", info);
    addGetterIfAvaliable(os, "processCpuTime", info);
    addGetterIfAvaliable(os, "totalPhysicalMemorySize", info);
    addGetterIfAvaliable(os, "totalSwapSpaceSize", info);

    // com.sun.management.UnixOperatingSystemMXBean
    addGetterIfAvaliable(os, "openFileDescriptorCount", info);
    addGetterIfAvaliable(os, "maxFileDescriptorCount", info);

    try {
      if (!os.getName().toLowerCase(Locale.ROOT).startsWith("windows")) {
        // Try some command line things
        info.put("uname", execute("uname -a"));
        info.put("uptime", execute("uptime"));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return info;
  }

  /**
   * Try to run a getter function. This is useful because java 1.6 has a few
   * extra useful functions on the <code>OperatingSystemMXBean</code>
   *
   * If you are running a sun jvm, there are nice functions in:
   * UnixOperatingSystemMXBean and com.sun.management.OperatingSystemMXBean
   *
   * it is package protected so it can be tested...
   */
  static void addGetterIfAvaliable(Object obj, String getter,
      Map<String, Object> info) {
    // This is a 1.6 function, so lets do a little magic to *try* to make it
    // work
    try {
      String n = Character.toUpperCase(getter.charAt(0)) + getter.substring(1);
      Method m = obj.getClass().getMethod("get" + n);
      m.setAccessible(true);
      Object v = m.invoke(obj, (Object[]) null);
      if (v != null) {
        info.put(getter, v);
      }
    } catch (Exception ex) {
    } // don't worry, this only works for 1.6
  }

  /**
   * Utility function to execute a function
   */
  private static String execute(String cmd) {
    DataInputStream in = null;
    Process process = null;

    try {
      process = Runtime.getRuntime().exec(cmd);
      in = new DataInputStream(process.getInputStream());
      // use default charset from locale here, because the command invoked
      // also uses the default locale:
      return IOUtils.toString(new InputStreamReader(in, Charset
          .defaultCharset()));
    } catch (Exception ex) {
      // ignore - log.warn("Error executing command", ex);
      return "(error executing: " + cmd + ")";
    } finally {
      if (process != null) {
        IOUtils.closeQuietly(process.getOutputStream());
        IOUtils.closeQuietly(process.getInputStream());
        IOUtils.closeQuietly(process.getErrorStream());
      }
    }
  }

  @RequestMapping()
  public ModelAndView home(@AuthenticationPrincipal UserEntity user,
      HttpServletRequest request, ModelAndView model,
      RedirectAttributes redirectAttributes) {

    ModelAndView mav = new ModelAndView("system");
    mav.addObject("user", user);
    return mav;
  }
}
