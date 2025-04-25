package com.example.analyticservice.dto;

import com.example.analyticservice.util.MachineState;
import lombok.Data;

@Data
public class MachineStateDTO {
    private Long machineId;
    private MachineState state;
}
