package com.searchbox.framework.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  SYSTEM("ROLE_SYSTEM"), ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

  private Role(final String text) {
    this.text = text;
  }

  private final String text;

  @Override
  public String getAuthority() {
    return this.text;
  }
}