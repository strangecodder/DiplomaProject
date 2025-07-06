package com.example.opcservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateNodeDTO {
    private String url;
    private String nodeName;
    private String description;
    private Double period;
}
