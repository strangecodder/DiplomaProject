package com.example.analyticservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachinesAnalyticDTO {
    private Long machineId;
    private String name;
    private String type;
    private Integer sensorsCount;
    private Long dataCount;
}
