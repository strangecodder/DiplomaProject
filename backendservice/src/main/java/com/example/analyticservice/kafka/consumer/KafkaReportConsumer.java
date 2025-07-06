package com.example.analyticservice.kafka.consumer;

import com.commondto.dto.ReportDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaReportConsumer {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String,Object> consumerReportConfigs(){
        Map<String,Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        JsonDeserializer<ReportDTO> jsonDeserializer = new JsonDeserializer<>(ReportDTO.class);
        jsonDeserializer.addTrustedPackages("com.example.commondto.dto");

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                jsonDeserializer);


        return props;
    }

    @Bean
    public ConsumerFactory<String, ReportDTO> consumerReportFactory(){
        JsonDeserializer<ReportDTO> jsonDeserializer = new JsonDeserializer<>(ReportDTO.class);
        jsonDeserializer.addTrustedPackages("com.example.analyticservice.dto"); // Убедитесь, что ваш пакет указан здесь
        jsonDeserializer.addTrustedPackages("com.commondto.dto");

        return new DefaultKafkaConsumerFactory<>(consumerReportConfigs(),new StringDeserializer(),new ErrorHandlingDeserializer<>(jsonDeserializer));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReportDTO> kafkaListenerReportContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,ReportDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerReportFactory());
        return factory;
    }
}
