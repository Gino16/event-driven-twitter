package com.gino.microservices.twittertokafkaservice.runner.impl;


import com.gino.microservices.configdata.TwitterToKafkaServiceConfigData;
import com.gino.microservices.twittertokafkaservice.runner.StreamRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import twitter4j.TwitterException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@ConditionalOnExpression("${twitter-to-kafka-service.twitter-v2.enabled} && not ${twitter-to-kafka-service.enable-mock-tweets}")
@RequiredArgsConstructor
@Slf4j
public class TwitterV2KafkaStreamRunner implements StreamRunner {

    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
    private final TwitterV2StreamHelper twitterV2StreamHelper;

    @Override
    public void start() throws TwitterException, IOException, URISyntaxException {
        String bearerToken = twitterToKafkaServiceConfigData.getTwitterV2().getBearerToken();
        if (bearerToken != null) {
            twitterV2StreamHelper.setupRules(bearerToken, getRules());
            twitterV2StreamHelper.connectStream(bearerToken);
        }

    }

    private Map<String, String> getRules() {
        return twitterToKafkaServiceConfigData.getTwitterKeywords().stream()
                .collect(Collectors.toMap(s -> s, o -> "keyword: " + o));
    }
}
