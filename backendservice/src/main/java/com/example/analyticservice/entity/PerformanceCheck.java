package com.example.analyticservice.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.sql.Timestamp;

@Entity
@Table(name = "performance_check")
@NoArgsConstructor
public class PerformanceCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "performance_check_seq")
    @SequenceGenerator(name = "performance_check_seq", sequenceName = "performance_check_sequence", allocationSize = 1)
    @Column(name = "performance_check_id")
    private Long performanceCheckId;

    @Column(name = "performance_type")
    private String performanceType;

    @Column(name = "performance_description")
    private String performanceDescription;

    @Column(name = "time")
    private Timestamp time;
}
