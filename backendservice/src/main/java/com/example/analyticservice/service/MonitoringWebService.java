package com.example.analyticservice.service;

import com.example.analyticservice.dto.SensorConfigDTO;
import com.example.analyticservice.dto.SensorMonitoringDTO;
import com.example.analyticservice.entity.Machine;
import com.example.analyticservice.entity.MachineSensor;
import com.example.analyticservice.entity.Sensor;
import com.example.analyticservice.repository.MachineRepository;
import com.example.analyticservice.repository.MachineSensorRepository;
import com.example.analyticservice.repository.SensorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonitoringWebService {

    private final SensorRepository sensorRepository;
    private final MachineSensorRepository machineSensorRepository;
    private final MachineRepository machineRepository;

    public ResponseEntity<List<SensorMonitoringDTO>> getMachineNodes(Long machineId) {
        //List<Sensor> sensors = machineSensorRepository.findSensorsByMachine(
          //      machineRepository.findById(machineId).get());

        List<MachineSensor> machineSensors = machineSensorRepository.findAll();

        List<Sensor> sensors = machineSensors.stream()
                .filter(machineSensor -> machineSensor.getMachine().getMachineId().equals(machineId))
                .map(sensor -> sensor.getSensor())
                .collect(Collectors.toList());

        List<SensorMonitoringDTO> monitoringDTOS = new ArrayList<>();

        for (Sensor sensor : sensors) {
            SensorMonitoringDTO sensorMonitoringDTO = new SensorMonitoringDTO();
            sensorMonitoringDTO.setSensorId(sensor.getSensorId());
            sensorMonitoringDTO.setSensorName(sensor.getSensorName());
            sensorMonitoringDTO.setNodeId(sensor.getNodeId());
            sensorMonitoringDTO.setMeasurementUnit(sensor.getMeasurementUnit());
            sensorMonitoringDTO.setOpcUaServer(sensor.getOpcUaServer());
            sensorMonitoringDTO.setPeriod(sensor.getPeriod());
            monitoringDTOS.add(sensorMonitoringDTO);
        }

        return new ResponseEntity<>(monitoringDTOS, HttpStatus.OK);
    }

    public void saveAndSend(SensorConfigDTO sensorConfigDTO){
        addNewSensorNode(sensorConfigDTO);


        //todo: потом добавить отправку по кафке
    }

    @Transactional
    public void addNewSensorNode(SensorConfigDTO sensorConfigDTO) {
        Sensor sensor = new Sensor();
        sensor.setSensorName(sensorConfigDTO.getSensorName());
        sensor.setSensorType(sensorConfigDTO.getSensorType());
        sensor.setSensorDescription(sensorConfigDTO.getSensorDescription());
        sensor.setMeasurementUnit(sensorConfigDTO.getMeasurementUnit());
        sensor.setOpcUaServer(sensorConfigDTO.getUrl());
        sensor.setNodeId(sensorConfigDTO.getNodeName());
        sensor.setPeriod(sensorConfigDTO.getPeriod());
        sensorRepository.save(sensor);
        addToMachineSensor(sensor.getSensorId(),sensorConfigDTO.getMachineId());
    }

    @Transactional
    public void addToMachineSensor(long sensorId, long machineId) {
        MachineSensor machineSensor = new MachineSensor();
        machineSensor.setSensor(sensorRepository.findById(sensorId).get());
        machineSensor.setMachine(machineRepository.findById(machineId).get());
        machineSensorRepository.save(machineSensor);
    }
}
