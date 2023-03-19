package com.gino.microservices.twittertokafkaservice.runner.impl;


import com.gino.microservices.configdata.config.TwitterToKafkaServiceConfigData;
import com.gino.microservices.twittertokafkaservice.listeners.TwitterToKafkaStatusListener;
import com.gino.microservices.twittertokafkaservice.runner.StreamRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import javax.annotation.PreDestroy;

@Slf4j
@ConditionalOnExpression("${twitter-to-kafka-service.enable-mock-tweets} && not ${twitter-to-kafka-service.twitter-v2.enabled}")
@RequiredArgsConstructor
public class TwitterKafkaStreamRunner implements StreamRunner {

    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
    private final TwitterToKafkaStatusListener twitterToKafkaStatusListener;
    private TwitterStream twitterStream;

    @Override
    public void start() throws TwitterException {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(twitterToKafkaStatusListener);
        addFilter();
    }

    private void addFilter() {
        FilterQuery filterQuery = new FilterQuery(twitterToKafkaServiceConfigData.getTwitterKeywords().toArray(new String[0]));
        twitterStream.filter(filterQuery);
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
