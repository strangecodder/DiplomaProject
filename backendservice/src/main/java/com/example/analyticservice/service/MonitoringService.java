package com.example.analyticservice.service;

import com.commondto.dto.SensorValueDTO;
import com.example.analyticservice.dto.CreateMachineDTO;
import com.example.analyticservice.dto.SensorConfigDTO;
import com.example.analyticservice.entity.Machine;
import com.example.analyticservice.entity.MachineData;
import com.example.analyticservice.entity.MachineSensor;
import com.example.analyticservice.entity.Sensor;
import com.example.analyticservice.repository.MachineDataRepository;
import com.example.analyticservice.repository.MachineRepository;
import com.example.analyticservice.repository.MachineSensorRepository;
import com.example.analyticservice.repository.SensorRepository;
import com.example.analyticservice.util.MachineState;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitoringService {

    private final MachineDataRepository machineDataRepository;
    private final MachineSensorRepository machineSensorRepository;
    private final MachineRepository machineRepository;
    private final SensorRepository sensorRepository;

    @Transactional
    public void saveMachine(CreateMachineDTO createMachineDTO){
        Machine machine = new Machine();
        machine.setName(createMachineDTO.getMachineName());
        machine.setType(createMachineDTO.getMachineType());
        machine.setState(MachineState.INACTIVE);
        machineRepository.save(machine);
    }

    @Transactional
    public void saveValue(SensorValueDTO sensorValueDTO) {
        MachineData machineData = new MachineData();
        Sensor sensor = machineSensorRepository.findMachineSensorBySensor_OpcUaServerAndSensor_NodeId(
                sensorValueDTO.getServerUrl(),
                sensorValueDTO.getNodeName()
        ).getSensor();

        machineData.setSensorId(sensor);
        machineData.setDataType("Снятие показания с датчика");
        machineData.setDataValue(sensorValueDTO.getValue());
        machineData.setDataTime(sensorValueDTO.getTime());
        machineDataRepository.save(machineData);
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
