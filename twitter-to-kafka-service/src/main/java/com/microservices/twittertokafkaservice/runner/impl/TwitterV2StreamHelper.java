package com.microservices.twittertokafkaservice.runner.impl;

import com.microservices.twittertokafkaservice.config.TwitterToKafkaServiceConfigData;
import com.microservices.twittertokafkaservice.listeners.TwitterToKafkaStatusListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.v1.Status;

@Slf4j
@ConditionalOnProperty(name = "twitter-to-kafka-service.twitter-v2.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
@Component
public class TwitterV2StreamHelper {

  private static final String tweetAsRawJson = "{" +
      "\"created_at\":\"{0}\"," +
      "\"id\":\"{1}\"," +
      "\"text\":\"{2}\"," +
      "\"user\":{\"id\":\"{3}\"}" +
      "}";
  private static final String TWITTER_STATUS_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
  private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
  private final TwitterToKafkaStatusListener twitterToKafkaStatusListener;

  /*
   * This method calls the filtered stream endpoint and streamsTweets from it
   * */
  void connectStream(String bearerToken)
      throws IOException, URISyntaxException, TwitterException, JSONException {
    CloseableHttpClient httpClient = HttpClients.custom()
        .setDefaultRequestConfig(
            RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()
        )
        .build();

    URIBuilder uriBuilder = new URIBuilder(
        twitterToKafkaServiceConfigData.getTwitterV2().getBaseUrl());

    HttpGet httpGet = new HttpGet(uriBuilder.build());
    httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));

    CloseableHttpResponse response = httpClient.execute(httpGet);
    HttpEntity entity = response.getEntity();

    if (entity != null) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
      String line = reader.readLine();
      while (line != null) {
        line = reader.readLine();
        if (!line.isEmpty()) {
          String tweet = getFormattedTweet(line);
          Status status = null;
          try {
            status = TwitterObjectFactory.createStatus(tweet);
          } catch (TwitterException e) {
            log.error("Could not create status for text: {}", tweet, e);
          }
          if (status != null) {
            twitterToKafkaStatusListener.onStatus(status);
          }
        }
      }
    }
  }

  private String getFormattedTweet(String data) {
    JSONObject jsonData = (JSONObject) new JSONObject(data).get("data");
    String[] params = new String[]{
        ZonedDateTime.parse(jsonData.get("created_at").toString())
            .withZoneSameInstant(ZoneId.of("UTC"))
            .format(DateTimeFormatter.ofPattern(TWITTER_STATUS_DATE_FORMAT, Locale.ENGLISH)),
        jsonData.get("id").toString(),
        jsonData.get("text").toString().replaceAll("\"", "\\\\\""),
        jsonData.get("author_id").toString()
    };
    return formatTweetAsJsonWithParams(params);
  }

  private String formatTweetAsJsonWithParams(String [] params) {
    String tweet = tweetAsRawJson;
    for (int i = 0; i < params.length; i++) {
      tweet = tweet.replace("{" + i "}", params[i]);
    }
    return tweet;
  }
}

