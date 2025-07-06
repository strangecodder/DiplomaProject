package com.example.opcservice.kafka.producer;

import com.commondto.dto.SensorValueDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaSensorDataSender {

    private final KafkaTemplate<String, SensorValueDTO> kafkaTemplate;

    public void sendDataToSave( SensorValueDTO message){
        kafkaTemplate.send("log-data",message);
    }

}
