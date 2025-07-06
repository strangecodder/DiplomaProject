package com.example.analyticservice.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic sensorValueTopic() {
        return TopicBuilder.name("log-data").build();
    }

    @Bean
    public NewTopic reportTopic() {
        return TopicBuilder.name("data-report").build();
    }

    @Bean
    public NewTopic fullReportTopic() {
        return TopicBuilder.name("full-report").build();
    }
}
