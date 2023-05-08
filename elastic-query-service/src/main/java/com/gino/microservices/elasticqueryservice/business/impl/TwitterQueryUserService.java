package com.gino.microservices.elasticqueryservice.business.impl;

import com.gino.microservices.elasticqueryservice.business.QueryUserService;
import com.gino.microservices.elasticqueryservice.entities.UserPermission;
import com.gino.microservices.elasticqueryservice.repositories.UserPermissionRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwitterQueryUserService implements QueryUserService {

  private final UserPermissionRepository userPermissionRepository;
  @Override
  public Optional<List<UserPermission>> findAllPermissionsByUsername(String username) {
    return userPermissionRepository.findPermissionsByUsername(username);
  }
}
