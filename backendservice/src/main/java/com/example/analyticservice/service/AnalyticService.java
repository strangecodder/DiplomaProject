package com.example.analyticservice.service;

import com.example.analyticservice.dto.ChartDTO;
import com.example.analyticservice.dto.MachinesAnalyticDTO;
import com.example.analyticservice.dto.MachinesDTO;
import com.example.analyticservice.entity.Machine;
import com.example.analyticservice.entity.MachineData;
import com.example.analyticservice.entity.MachineSensor;
import com.example.analyticservice.entity.Sensor;
import com.example.analyticservice.repository.MachineDataRepository;
import com.example.analyticservice.repository.MachineRepository;
import com.example.analyticservice.repository.MachineSensorRepository;
import com.example.analyticservice.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticService {

    private final MachineSensorRepository machineSensorRepository;
    private final MachineRepository machineRepository;
    private final MachineDataRepository machineDataRepository;
    private final SensorRepository sensorRepository;


    public List<MachinesAnalyticDTO> getMachinesBySensorId() {
          List<MachinesAnalyticDTO> analyticDTOS = machineRepository.findAll().stream()
                .map(machine -> new MachinesAnalyticDTO(
                        machine.getMachineId(),
                        machine.getName(),
                        machine.getType(),
                        machineSensorRepository.countSensorsByMachine(machine),
                        getLogsCount(machine.getMachineId())
                )).
                toList();
        return analyticDTOS;
    }

    private long getLogsCount(long machineId) {
        List<MachineSensor> sensors = machineSensorRepository.findByMachine(
                machineRepository.findById(machineId).get()
        );


        return sensors.stream()
                .mapToLong(sensor -> machineDataRepository.countMachineDataBySensorId(sensor.getSensor()))
                .sum();
    }

    public List<MachineData> getLogsByMachineId(Long machineId) {
        return machineSensorRepository.findByMachine(
                        machineRepository.findById(machineId).get()).
                stream().
                flatMap(sensor -> machineDataRepository.findAllBySensorId(sensor.getSensor()).stream())
                .toList();
    }

    public List<Sensor> getSensorsByMachineId(Long machineId) {
        return machineSensorRepository.findByMachine(
                machineRepository.findById(machineId).get()
                ).
                stream().
                map(MachineSensor::getSensor).
                toList();
    }

    public List<MachineData> getChartData(ChartDTO chartDTO) {
        return machineDataRepository.findAllBySensorId(
                sensorRepository.findById(chartDTO.getSensorId()).get()).
                stream().
                filter(machineData -> machineData.getDataTime().getTime()>=chartDTO.getStartTime()
                        && machineData.getDataTime().getTime()<=chartDTO.getEndTime()).
                toList();
    }
}

