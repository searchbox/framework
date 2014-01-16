package com.searchbox.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Account {

    /**
     */
    private String username;

    /**
     */
    private String email;

    /**
     */
    private String password;

    /**
     */
    private String fullName;
}
