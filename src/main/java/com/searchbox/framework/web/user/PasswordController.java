package com.searchbox.framework.web.user;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.searchbox.framework.model.UserEntity;
import com.searchbox.framework.repository.UserRepository;
import com.searchbox.framework.service.UserService;
import com.searchbox.framework.util.SecurityUtil;

@Controller
@RequestMapping("/user/**")
public class PasswordController {

  @Resource
  Environment env;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(PasswordController.class);

  @Autowired
  private UserRepository repository;

  @Autowired
  private UserService service;

  private boolean tokenIsValid(UserEntity user) {
    // check that token is still valid
    Date referenceDate = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(referenceDate);
    c.add(Calendar.DAY_OF_MONTH, -1);
    return user.getResetDate().after(c.getTime());
  }

  @RequestMapping(value = "/resetPassword/{token:.+}", method = RequestMethod.GET)
  public ModelAndView resetPassword(@PathVariable String token) {

    UserEntity user = repository.findByResetHash(token);
    ModelAndView mav = new ModelAndView("user/passwordReset");

    if (!tokenIsValid(user)) {
      // The token is not valid anymore
      LOGGER.info("Token is expired for user {}", user.getEmail());
    } else {
      mav.addObject("user", user);
    }

    // Provide resetForm
    mav.addObject("token", token);
    return mav;
  }

  @RequestMapping(value = "/resetPassword/{token:.+}", method = RequestMethod.POST)
  public ModelAndView resetPassword(@PathVariable String token,
      String password, String passwordConfirm, WebRequest request) {

    UserEntity user = repository.findByResetHash(token);
    ModelAndView mav = new ModelAndView("user/passwordReset");

    if (!tokenIsValid(user)) {
      LOGGER.info("Token is expired for user {}", user.getEmail());
      return mav;
    }

    if (!password.equals(passwordConfirm)) {
      LOGGER.info("Passwords do not match!");
    }

    // Ok we change the password of the user
    user = service.changePassword(user, password);

    // And we log him in (conveniance)
    SecurityUtil.logInUser(user);
    ProviderSignInUtils.handlePostSignUp(user.getEmail(), request);
    LOGGER.info("User {} has been signed in", user.getEmail());

    return new ModelAndView(new RedirectView("/", true));
  }

  
  @RequestMapping(value = "/reset", method = RequestMethod.GET,
      params={"email"})
  @ResponseBody
  public Map<String,String> requestPasswordReset(
      @RequestParam(value = "email") String email,
      HttpServletRequest request) {
     
    Map<String, String> result = new HashMap<>();
    
    if (!service.emailExist(email)) {
      LOGGER.info("User with email {} does not exists", email);
      result.put("status", "KO");
      result.put("message", "User with email \""+email+"\" does not exists");
      return result;
    }

    String host = env.getProperty("searchbox.dns");
    if (host == null || host.isEmpty()) {
      host = request.getRemoteHost();
    }
    LOGGER.debug("host for reset is {}", host);

    Integer port = Integer.parseInt(env.getProperty("searchbox.port"));
    if (port == null) {
      port = request.getRemotePort();
    }
    LOGGER.debug("Port for reset is {}", port);

    String path = request.getContextPath();
    LOGGER.debug("Context path for reset is {}", path);

    String hash = service.getResetHash(email);
    LOGGER.debug("Hash for reset is {}", hash);

    String resetLink = service.resetPasswordWithEmail(email, host, port, path);
    LOGGER.info("Reset password link for {} is {}", email, resetLink);

    result.put("status", "OK");

    return result;
  }

}
