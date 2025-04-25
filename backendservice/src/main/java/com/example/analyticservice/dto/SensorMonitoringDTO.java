package com.example.analyticservice.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SensorMonitoringDTO {
    private Long sensorId;
    private String sensorName;
    private String nodeId;
    private String measurementUnit;
    private Double value;
    private String opcUaServer;
    private Double period;
}
