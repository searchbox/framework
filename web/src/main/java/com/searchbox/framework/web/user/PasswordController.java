package com.searchbox.framework.web.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.searchbox.framework.service.UserService;

@Controller
@SessionAttributes("user")
@RequestMapping("/user/**")
public class PasswordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordController.class);

    private UserService service;
    private static final String EMAIL_PARAM_NAME = "email";
    
    @Autowired
    public PasswordController(UserService service) {
        this.service = service;
    }

    

   
 }
