package com.example.analyticservice.entity;

import com.example.analyticservice.util.MachineState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "machine")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "machine_seq")
    @SequenceGenerator(name = "machine_seq", sequenceName = "machine_sequence", allocationSize = 1)
    @Column(name = "machine_id")
    private Long machineId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "opc_server")
    private String opcServer;

    @Column(name = "state")
    private MachineState state;
}
