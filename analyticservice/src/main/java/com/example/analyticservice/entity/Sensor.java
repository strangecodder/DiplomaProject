package com.example.analyticservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sensor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensor_seq")
    @SequenceGenerator(name = "sensor_seq", sequenceName = "sensor_sequence", allocationSize = 1)
    @Column(name = "sensor_id")
    private Long sensorId;

    @Column(name = "sensor_name")
    private String sensorName;

    @Column(name = "sensor_type")
    private String sensorType;

    @Column(name = "sensor_description")
    private String sensorDescription;

    @Column(name = "opc_ua_server")
    private String opcUaServer;

    @Column(name = "node_id")
    private String nodeId;

    @Column(name = "period")
    private Double period;

    @Column(name = "measurement_unit")
    private String measurementUnit;

    @Column(name = "location")
    private String location;
}
