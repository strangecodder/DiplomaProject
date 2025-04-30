package com.example.analyticservice.kafka.listener;

import com.commondto.dto.SensorValueDTO;
import com.example.analyticservice.service.MonitoringService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@RequiredArgsConstructor
public class KafkaOpcListener {

    private final MonitoringService monitoringService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "log-data", groupId = "group1",containerFactory = "kafkaListenerContainerFactory")
    void listener(SensorValueDTO sensorValueDTO) {
        System.out.println("Я в листинере");
        System.out.println(sensorValueDTO.toString());
        monitoringService.saveValue(sensorValueDTO);
//        try {
//            // Десериализация JSON-сообщения в объект SensorValueDTO
//            SensorValueDTO sensorValue = objectMapper.readValue(sensorValueMessage, SensorValueDTO.class);
//
//            // Обработка полученного объекта
//            System.out.println("Received sensor value: " + sensorValue.getValue() + " from sensor ID: " + sensorValue.getNodeName());
//        } catch (Exception e) {
//            // Обработка ошибок десериализации
//            System.err.println("Failed to deserialize message: " + sensorValueMessage);
//            e.printStackTrace();
//        }

    }
}
