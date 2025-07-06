package com.example.analyticservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workplace")
@NoArgsConstructor
public class Workplace {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workplace_seq")
    @SequenceGenerator(name = "workplace_seq", sequenceName = "workplace_sequence", allocationSize = 1)
    @Column(name = "workplace_id")
    private Long workplaceId;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;
}
