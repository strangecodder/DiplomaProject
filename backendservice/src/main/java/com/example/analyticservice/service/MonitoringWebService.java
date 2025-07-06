package com.example.analyticservice.service;

import com.example.analyticservice.dto.CreateMachineDTO;
import com.example.analyticservice.dto.MachinesDTO;
import com.example.analyticservice.dto.SensorConfigDTO;
import com.example.analyticservice.dto.SensorMonitoringDTO;
import com.example.analyticservice.entity.Machine;
import com.example.analyticservice.entity.MachineSensor;
import com.example.analyticservice.entity.Sensor;
import com.example.analyticservice.repository.MachineRepository;
import com.example.analyticservice.repository.MachineSensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitoringWebService {

    private final MachineSensorRepository machineSensorRepository;
    private final MachineRepository machineRepository;
    private final MonitoringService monitoringService;

    public List<MachinesDTO> getAllMachines(){
        return  machineRepository.findAll().
                stream().
                map(machine -> new MachinesDTO(machine.getMachineId(),
                        machine.getName(),
                        machine.getType(),
                        machine.getState(),
                        machineSensorRepository.countSensorsByMachine(machine)
                )).
                toList();
    }

    public Machine getMachineById(Long id){
        return machineRepository.findById(id).orElse(null);
    }

    public List<SensorMonitoringDTO> getMachineNodes(Long machineId) {

        List<Sensor> sensors = machineSensorRepository.findAll().stream()
                .filter(machineSensor -> machineSensor.getMachine().getMachineId().equals(machineId))
                .map(MachineSensor::getSensor)
                .toList();

        List<SensorMonitoringDTO> monitoringDTOS = sensors.stream()
                .map(this::convertToMonitoringDTO)
                .toList();

        return monitoringDTOS;
    }

    private SensorMonitoringDTO convertToMonitoringDTO(Sensor sensor) {
        return new SensorMonitoringDTO(
                sensor.getSensorId(),
                sensor.getSensorName(),
                sensor.getNodeId(),
                sensor.getMeasurementUnit(),
                0d,
                sensor.getOpcUaServer(),
                sensor.getPeriod()
        );
    }


    public void saveMachine(CreateMachineDTO createMachineDTO){
        monitoringService.saveMachine(createMachineDTO);
    }

    public void addNewSensor(SensorConfigDTO sensorConfigDTO) {
        monitoringService.addNewSensorNode(sensorConfigDTO);
    }

}