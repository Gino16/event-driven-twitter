package com.gino.microservices.elasticquerywebclient.config;

import com.gino.microservices.configdata.config.ElasticQueryWebClientConfigData;
import com.gino.microservices.configdata.config.UserConfigData;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

  private final ElasticQueryWebClientConfigData elasticQueryWebClientConfigData;
  private final UserConfigData userConfigData;

  @LoadBalanced
  @Bean("webClientBuilder")
  WebClient.Builder webClientBuilder() {
    return WebClient.builder()
        .filter(ExchangeFilterFunctions
            .basicAuthentication(userConfigData.getUsername(), userConfigData.getPassword()))
        .baseUrl(elasticQueryWebClientConfigData.getWebClient().getBaseUrl())
        .defaultHeader(HttpHeaders.CONTENT_TYPE,
            elasticQueryWebClientConfigData.getWebClient().getContentType())
        .defaultHeader(HttpHeaders.ACCEPT,
            elasticQueryWebClientConfigData.getWebClient().getAcceptType())
        .clientConnector(new ReactorClientHttpConnector(HttpClient.from(getTcpClient())))
        .codecs(configurer -> configurer.defaultCodecs()
            .maxInMemorySize(elasticQueryWebClientConfigData.getWebClient().getMaxInMemorySize()));
  }

  private TcpClient getTcpClient() {
    return TcpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
            elasticQueryWebClientConfigData.getWebClient().getConnectTimeoutMs())
        .doOnConnected(connection -> {
          connection.addHandlerLast(new ReadTimeoutHandler(
              elasticQueryWebClientConfigData.getWebClient().getReadTimeoutMs(),
              TimeUnit.MILLISECONDS));
          connection.addHandlerLast(new WriteTimeoutHandler(
              elasticQueryWebClientConfigData.getWebClient().getWriteTimeoutMs(),
              TimeUnit.MILLISECONDS));
        });
  }
}
