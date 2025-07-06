package com.example.opcservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {
    private String nodeId;
    private Double value;
    private Timestamp time;
}
