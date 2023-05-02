package com.gino.microservices.elasticqueryservice.security;

import com.gino.microservices.configdata.config.ElasticQueryServiceConfigData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@Qualifier("elastic-query-service-audience-validator")
@RequiredArgsConstructor
public class AudienceValidator implements OAuth2TokenValidator<Jwt> {

  private final ElasticQueryServiceConfigData elasticQueryServiceConfigData;

  @Override
  public OAuth2TokenValidatorResult validate(Jwt jwt) {
    if (jwt.getAudience().contains(elasticQueryServiceConfigData.getCustomAudience())) {
      return OAuth2TokenValidatorResult.success();
    } else {
      return OAuth2TokenValidatorResult.failure(
          new OAuth2Error("invalid_token",
              "The required audience " + elasticQueryServiceConfigData.getCustomAudience()
                  + " is missing!", null));
    }
  }
}
