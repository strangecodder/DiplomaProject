package com.example.analyticservice.controller;

import com.example.analyticservice.dto.*;
import com.example.analyticservice.entity.Machine;
import com.example.analyticservice.repository.MachineRepository;
import com.example.analyticservice.repository.MachineSensorRepository;
import com.example.analyticservice.service.MonitoringWebService;
import com.example.analyticservice.util.MachineState;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/monitoring/")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class MonitoringController {

    private final MonitoringWebService monitoringWebService;
    private final MachineRepository machineRepository;

    @GetMapping("/allMachines")
    public List<MachinesDTO> getAllMachines() {
        return monitoringWebService.getAllMachines();
    }

    @GetMapping("/machineNodes")
    public List<SensorMonitoringDTO> getMachineNodes(@RequestParam Long machineId) {
        return monitoringWebService.getMachineNodes(machineId);
    }

    @GetMapping("/machineInfo")
    public Machine getMachine(@RequestParam Long machineId) {
        return monitoringWebService.getMachineById(machineId);
    }

    @PostMapping("/createMachine")
    public void createMachine(@RequestBody CreateMachineDTO newMachine) {
        monitoringWebService.saveMachine(newMachine);
    }

    @PostMapping("/setMachineState")
    @Transactional
    public void setMachineState(@RequestBody MachineStateDTO machineStateDTO) {
        Machine machine = new Machine();
        machine.setState(machineStateDTO.getState());
        machineRepository.save(machine);
    }

    @PostMapping("/addSensorConfig")
    public void addSensor(@RequestBody SensorConfigDTO sensorConfigDTO){
        monitoringWebService.addNewSensor(sensorConfigDTO);
    }
}
