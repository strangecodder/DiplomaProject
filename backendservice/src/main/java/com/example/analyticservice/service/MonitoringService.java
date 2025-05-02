package com.example.analyticservice.service;

import com.commondto.dto.SensorValueDTO;
import com.example.analyticservice.entity.Machine;
import com.example.analyticservice.entity.MachineData;
import com.example.analyticservice.entity.MachineSensor;
import com.example.analyticservice.entity.Sensor;
import com.example.analyticservice.repository.MachineDataRepository;
import com.example.analyticservice.repository.MachineSensorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitoringService {

    private final MachineDataRepository machineDataRepository;
    private final MachineSensorRepository machineSensorRepository;

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
}
