package com.example.opcservice.service;

import com.example.opcservice.dto.SensorValueDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocketSendService {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public void sendMessage(String sensorId, Double value) {
        SensorValueDto valueDto = new SensorValueDto();
        valueDto.setSensorId(sensorId);
        valueDto.setValue(value);
        messagingTemplate.convertAndSend("/topic/messages", valueDto);
        System.out.println("Message sent");
    }

}
