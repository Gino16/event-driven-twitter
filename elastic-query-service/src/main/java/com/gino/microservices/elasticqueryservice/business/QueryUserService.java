package com.gino.microservices.elasticqueryservice.business;

import com.gino.microservices.elasticqueryservice.entities.UserPermission;
import java.util.List;
import java.util.Optional;

public interface QueryUserService {

  Optional<List<UserPermission>> findAllPermissionsByUsername(String username);
}
