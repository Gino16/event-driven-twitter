package com.gino.microservices.twittertokafkaservice.listeners;

import com.gino.microservices.configdata.config.KafkaConfigData;
import com.gino.microservices.kafkaproducer.service.KafkaProducer;
import com.gino.microservices.twittertokafkaservice.transformer.TwitterStatusToAvroTransformer;
import com.microservices.kafka.avro.model.TwitterAvroModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Slf4j
@RequiredArgsConstructor
@Component
public class TwitterToKafkaStatusListener extends StatusAdapter {

    private final KafkaConfigData kafkaConfigData;
    private final KafkaProducer<Long, TwitterAvroModel> kafkaProducer;
    private final TwitterStatusToAvroTransformer twitterStatusToAvroTransformer;

    @Override
    public void onStatus(Status status) {
        log.info("Received status: {} and sending to kafka topic {}", status.getText(), kafkaConfigData.getTopicName());
        TwitterAvroModel twitterAvroModel = twitterStatusToAvroTransformer.getTwitterAvroModelFromStatus(status);
        kafkaProducer.send(kafkaConfigData.getTopicName(), twitterAvroModel.getId(), twitterAvroModel);
    }
}
