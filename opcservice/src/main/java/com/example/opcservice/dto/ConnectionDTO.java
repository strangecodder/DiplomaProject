package com.example.opcservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class ConnectionDTO {
    private String url;
    private String nodeId;
    private Double period;
}
