package com.gino.microservices.configdata.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "user-config")
public class UserConfigData {
  private String username;
  private String password;
  private List<String> roles;
}
