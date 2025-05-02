package com.commondto.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReportDTO {
    private Long code;
    private String username;
    private Long machineId;
    private Long sensorId;
    private String reportDescription;
    private Timestamp reportDate;
    private String imageData;

}

