package com.gino.microservices.elasticqueryservice.security;

import com.gino.microservices.elasticqueryservice.business.QueryUserService;
import com.gino.microservices.elasticqueryservice.transformer.UserPermissionsToUserDetailTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwitterQueryUserDetailsService implements UserDetailsService {

  private final QueryUserService queryUserService;
  private final UserPermissionsToUserDetailTransformer userPermissionsToUserDetailTransformer;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return queryUserService
        .findAllPermissionsByUsername(username)
        .map(userPermissionsToUserDetailTransformer::getUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException("No user found with name " + username));
/*    return TwitterQueryUser.builder()
        .username(username)
        .build();*/
  }
}
