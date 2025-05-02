package com.example.analyticservice.kafka.listener;

import com.commondto.dto.ReportDTO;
import com.commondto.dto.SensorValueDTO;
import com.example.analyticservice.kafka.producer.KafkaFullReportSender;
import com.example.analyticservice.service.MonitoringService;
import com.example.analyticservice.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@RequiredArgsConstructor
public class KafkaOpcListener {

    private final MonitoringService monitoringService;
    private final ReportService reportService;
    private final KafkaFullReportSender kafkaFullReportSender;

    @KafkaListener(topics = "log-data", groupId = "group1",containerFactory = "kafkaListenerContainerFactory")
    void listener(SensorValueDTO sensorValueDTO) {
        monitoringService.saveValue(sensorValueDTO);
    }

    @KafkaListener(topics = "data-report",groupId = "report_group",containerFactory = "kafkaListenerReportContainerFactory")
    void reportListener(ReportDTO reportDTO) {
        kafkaFullReportSender.sendFullReport(reportService.createFullReport(reportDTO));
    }

}
