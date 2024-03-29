package com.gino.microservices.elasticqueryservice.config;

import com.gino.microservices.configdata.config.UserConfigData;
import com.gino.microservices.elasticqueryservice.security.TwitterQueryUserDetailsService;
import com.gino.microservices.elasticqueryservice.security.TwitterQueryUserJwtConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserConfigData userConfigData;
  private final TwitterQueryUserDetailsService twitterQueryUserDetailsService;
  private final OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .anyRequest()
        .fullyAuthenticated()
        .and()
        .oauth2ResourceServer()
        .jwt()
        .jwtAuthenticationConverter(twitterQueryJwtConverter());
/*    http
        .httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers("/**").hasRole("USER")
        .and()
        .csrf().disable();*/
  }

  @Bean
  JwtDecoder jwtDecoder(
      @Qualifier("elastic-query-service-audience-validator") OAuth2TokenValidator<Jwt> audienceValidator) {
    NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(
        oAuth2ResourceServerProperties.getJwt().getIssuerUri()
    );
    OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(
        oAuth2ResourceServerProperties.getJwt().getIssuerUri()
    );
    OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer,
        audienceValidator);
    jwtDecoder.setJwtValidator(withAudience);
    return jwtDecoder;
  }

  @Bean
  Converter<Jwt, ? extends AbstractAuthenticationToken> twitterQueryJwtConverter() {
    return new TwitterQueryUserJwtConverter(twitterQueryUserDetailsService);
  }
/*  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .inMemoryAuthentication()
        .withUser(userConfigData.getUsername())
        .password(passwordEncoder().encode(userConfigData.getPassword()))
        .roles(userConfigData.getRoles().toArray(new String[0]));
  }*/

  @Bean
  protected PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
