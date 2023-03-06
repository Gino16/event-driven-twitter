package com.microservices.twittertokafkaservice.config;

import lombok.Data;

@Data
public class TwitterV2 {
    private boolean enabled;
    private String baseUrl;
    private String ruleBaseUrl;
    private String bearerToken;
}
