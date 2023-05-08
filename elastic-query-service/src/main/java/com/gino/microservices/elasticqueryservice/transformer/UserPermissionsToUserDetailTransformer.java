package com.gino.microservices.elasticqueryservice.transformer;

import com.gino.microservices.elasticqueryservice.entities.UserPermission;
import com.gino.microservices.elasticqueryservice.security.PermissionType;
import com.gino.microservices.elasticqueryservice.security.TwitterQueryUser;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionsToUserDetailTransformer {

  public TwitterQueryUser getUserDetails(List<UserPermission> userPermissions) {
    return TwitterQueryUser.builder()
        .username(userPermissions.get(0).getUsername())
        .permissions(userPermissions.stream()
            .collect(Collectors.toMap(
                UserPermission::getDocumentId,
                userPermission -> PermissionType.valueOf(userPermission.getPermissionType()))
            ))
        .build();
  }
}
