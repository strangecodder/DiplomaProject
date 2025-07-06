package com.example.analyticservice.dto;

import com.example.analyticservice.util.MachineState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class MachinesDTO {
    private Long machineId;
    private String machineName;
    private String machineType;
    private MachineState state;
    private Integer sensorsNumber;
}
