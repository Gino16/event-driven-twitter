package com.gino.microservices.elasticqueryservice.security;

import static com.gino.microservices.elasticqueryservice.Constants.NA;

import java.util.Collection;
import java.util.Map;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
public class TwitterQueryUser implements UserDetails {

  private String username;
  private Collection<? extends GrantedAuthority> authorities;
  private Map<String, PermissionType> permissions;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  @Override
  public String getPassword() {
    return NA;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public Map<String, PermissionType> getPermissions() {
    return permissions;
  }
}
