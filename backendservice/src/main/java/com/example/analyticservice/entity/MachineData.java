package com.example.analyticservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "machine_action")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MachineData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "machine_data_seq")
    @SequenceGenerator(name = "machine_data_seq", sequenceName = "machine_data_sequence", allocationSize = 1)
    @Column(name = "machine_data_id")
    private Long machineDataId;

    @ManyToOne
    @JoinColumn(name = "sensor_id",referencedColumnName = "sensor_id")
    private Sensor sensorId;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "data_value")
    private Double dataValue;

    @Column(name = "data_time")
    private Timestamp dataTime;
}
