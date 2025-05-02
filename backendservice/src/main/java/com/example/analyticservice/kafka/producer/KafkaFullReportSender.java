package com.example.analyticservice.kafka.producer;

import com.commondto.dto.FullReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaFullReportSender {

    private final KafkaTemplate<String, FullReportDTO> kafkaFullReportTemplate;

    public void sendFullReport(FullReportDTO fullReportDTO){
        kafkaFullReportTemplate.send("full-report", fullReportDTO);
    }
}
