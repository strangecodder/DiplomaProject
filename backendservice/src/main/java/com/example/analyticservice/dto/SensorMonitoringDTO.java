package com.example.analyticservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorMonitoringDTO {
    private Long sensorId;
    private String sensorName;
    private String nodeId;
    private String measurementUnit;
    private Double value;
    private String opcUaServer;
    private Double period;
}
