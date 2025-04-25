package com.example.analyticservice.dto;

import lombok.Data;

@Data
public class SensorConfigDTO {
    private String sensorName;
    private String sensorType;
    private String sensorDescription;
    private String measurementUnit;
    private String url;
    private String nodeName;
    private String description;
    private Double period;
    private Long machineId;
}
