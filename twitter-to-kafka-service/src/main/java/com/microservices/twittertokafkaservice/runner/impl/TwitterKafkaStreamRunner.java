package com.microservices.twittertokafkaservice.runner.impl;

import com.microservices.configserver.config.TwitterToKafkaServiceConfigData;
import com.microservices.twittertokafkaservice.listeners.TwitterToKafkaStatusListener;
import com.microservices.twittertokafkaservice.runner.StreamRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.v1.FilterQuery;
import twitter4j.v1.TwitterStream;

import javax.annotation.PreDestroy;

@Slf4j
@ConditionalOnExpression("${twitter-to-kafka-service.enable-mock-tweets} && not ${twitter-to-kafka-service.twitter-v2.enabled} ")
@RequiredArgsConstructor
public class TwitterKafkaStreamRunner implements StreamRunner {

    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
    private final TwitterToKafkaStatusListener twitterToKafkaStatusListener;
    TwitterStream twitterStream;

    @Override
    public void start() throws TwitterException {
        twitterStream = Twitter.newBuilder()
                .listener(twitterToKafkaStatusListener)
                .build().v1().stream();
        addFilter();
    }

    private void addFilter() {
        twitterStream.filter(FilterQuery.ofTrack(twitterToKafkaServiceConfigData.getTwitterKeywords().toArray(new String[0])));
        log.info("Started filtering twitter stream of keywords");
    }

    @PreDestroy
    public void shutdown() {
        if (twitterStream != null) {
            log.info("Closing twitter stream!");
            twitterStream.shutdown();
        }
    }
}
