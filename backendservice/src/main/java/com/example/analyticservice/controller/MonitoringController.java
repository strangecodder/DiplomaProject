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
    private final MachineSensorRepository machineSensorRepository;

    @GetMapping("/allMachines")
    public ResponseEntity<List<MachinesDTO>> getAllMachines() {
        List<Machine> machines = machineRepository.findAll();
        List<MachinesDTO> response = machines.
                stream().
                map(machine -> new MachinesDTO(machine.getMachineId(),
                        machine.getName(),
                        machine.getType(),
                        machine.getState(),
                        machineSensorRepository.countSensorsByMachine(machine)
                )).
                toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/machineNodes")
    public ResponseEntity<List<SensorMonitoringDTO>> getMachineNodes(@RequestParam Long machineId) {
        return monitoringWebService.getMachineNodes(machineId);
    }

    @GetMapping("/machineInfo")
    public ResponseEntity<Machine> getMachine(@RequestParam Long machineId) {
        return new ResponseEntity<>(machineRepository.findById(machineId).get(), HttpStatus.OK);
    }

    @PostMapping("/createMachine")
    @Transactional
    public void createMachine(@RequestBody CreateMachineDTO newMachine) {
        Machine machine = new Machine();
        machine.setName(newMachine.getMachineName());
        machine.setType(newMachine.getMachineType());
        machine.setState(MachineState.INACTIVE);
        machineRepository.save(machine);
    }

    @PostMapping("/setMachineState")
    @Transactional
    public ResponseEntity<HttpStatus> setMachineState(@RequestBody MachineStateDTO machineStateDTO) {
        Machine machine = new Machine();
        machine.setState(machineStateDTO.getState());
        machineRepository.save(machine);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addSensorConfig")
    public ResponseEntity<HttpStatus> addSensor(@RequestBody SensorConfigDTO sensorConfigDTO){
        monitoringWebService.addNewSensorNode(sensorConfigDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
