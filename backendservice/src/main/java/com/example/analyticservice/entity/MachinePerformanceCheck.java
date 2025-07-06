package com.example.analyticservice.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "machine_performanche_check")
@NoArgsConstructor
public class MachinePerformanceCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "machine_performance_check_seq")
    @SequenceGenerator(name = "machine_performance_check_seq", sequenceName = "machine_performance_check_sequence", allocationSize = 1)
    @Column(name = "machine_performance_check_id")
    private Long machinePerformanceCheckId;

    @ManyToOne
    @JoinColumn(name = "mp_machine_id",referencedColumnName = "machine_id")
    private Machine machineId;

    @ManyToOne
    @JoinColumn(name = "mp_performance_check_id",referencedColumnName = "performance_check_id")
    private PerformanceCheck performanceCheckId;
}
