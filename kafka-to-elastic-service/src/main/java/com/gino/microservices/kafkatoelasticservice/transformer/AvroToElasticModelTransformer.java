package com.gino.microservices.kafkatoelasticservice.transformer;

import com.gino.microservices.elasticmodel.model.index.impl.TwitterIndexModel;
import com.microservices.kafka.avro.model.TwitterAvroModel;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AvroToElasticModelTransformer {

  public List<TwitterIndexModel> getElasticModels(List<TwitterAvroModel> avroModels) {
    return avroModels.stream()
        .map(avroModel -> TwitterIndexModel
            .builder()
            .userId(avroModel.getUserId())
            .id(String.valueOf(avroModel.getId()))
            .text(avroModel.getText())
            .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(avroModel.getCreatedAt()),
                ZoneId.systemDefault()))
            .build()
        ).collect(Collectors.toList());
  }

}
