package com.reportservice.kafka.producer;

import com.commondto.dto.ReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaReportSender {

    private final KafkaTemplate<String, ReportDTO> kafkaReportTemplate;

    public void sendReport(ReportDTO reportDTO) {
        kafkaReportTemplate.send("data-report", reportDTO);
    }
}
