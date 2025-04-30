package com.commondto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SensorValueDTO.class, name = "sensorValueDTO")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorValueDTO {

    @JsonProperty("serverUrl")
    private String serverUrl;

    @JsonProperty("nodeName")
    private String nodeName;

    @JsonProperty("value")
    private Double value;

    @JsonProperty("time")
    private Timestamp time;
}
