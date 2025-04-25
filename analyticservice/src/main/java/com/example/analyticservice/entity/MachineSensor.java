package com.example.analyticservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "machine_sensor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MachineSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "machine_sensor_seq")
    @SequenceGenerator(name = "machine_sensor_seq", sequenceName = "machine_sensor_sequence", allocationSize = 1)
    @Column(name = "machine_sensor_id")
    private Long machineSensorId;

    @ManyToOne
    @JoinColumn(name = "ms_machine_id",referencedColumnName = "machine_id")
    private Machine machine;

    @ManyToOne
    @JoinColumn(name = "ms_sensor_id",referencedColumnName = "sensor_id")
    private Sensor sensor;
}
