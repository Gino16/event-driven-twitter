package com.gino.microservices.twittertokafkaservice;

import com.gino.microservices.configdata.TwitterToKafkaServiceConfigData;
import com.gino.microservices.twittertokafkaservice.init.StreamInitializer;
import com.gino.microservices.twittertokafkaservice.runner.StreamRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 */
@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.gino.microservices")
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;

    private final StreamRunner streamRunner;
    private final StreamInitializer streamInitializer;
    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Hello world");
        streamInitializer.init();
        streamRunner.start();
    }
}
