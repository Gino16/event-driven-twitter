package com.gino.microservices.configdata.config;

import lombok.Data;

@Data
public class TwitterV2 {
    private boolean enabled;
    private String baseUrl;
    private String rulesBaseUrl;
    private String bearerToken;
}
