package com.commondto.dto;

import lombok.Data;

@Data
public class FullReportDTO {
    private String username;

    private String machineName;
    private String machineType;

    private String sensorName;
    private String sensorType;
    private String opcServer;
    private String nodeName;

    private String reportDescription;
    private String reportDate;
    private String imageData;
}
