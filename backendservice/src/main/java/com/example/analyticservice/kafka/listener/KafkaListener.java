package com.example.analyticservice.kafka.listener;

import com.commondto.dto.ReportDTO;
import com.commondto.dto.SensorValueDTO;
import com.example.analyticservice.kafka.producer.KafkaFullReportSender;
import com.example.analyticservice.service.MonitoringService;
import com.example.analyticservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KafkaListener {

    private final MonitoringService monitoringService;
    private final ReportService reportService;
    private final KafkaFullReportSender kafkaFullReportSender;

    @org.springframework.kafka.annotation.KafkaListener(topics = "log-data", groupId = "group1",containerFactory = "kafkaListenerContainerFactory")
    void listener(SensorValueDTO sensorValueDTO) {
        monitoringService.saveValue(sensorValueDTO);
    }

    @org.springframework.kafka.annotation.KafkaListener(topics = "data-report",groupId = "report_group",containerFactory = "kafkaListenerReportContainerFactory")
    void reportListener(ReportDTO reportDTO) {
        kafkaFullReportSender.sendFullReport(reportService.createFullReport(reportDTO));
    }

}
