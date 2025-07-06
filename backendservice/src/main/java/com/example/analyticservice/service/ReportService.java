package com.example.analyticservice.service;

import com.commondto.dto.FullReportDTO;
import com.commondto.dto.ReportDTO;
import com.example.analyticservice.entity.Machine;
import com.example.analyticservice.entity.Sensor;
import com.example.analyticservice.repository.MachineRepository;
import com.example.analyticservice.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final MachineRepository machineRepository;
    private final SensorRepository sensorRepository;

    public FullReportDTO createFullReport(ReportDTO dto) {
        Machine machine = machineRepository.findById(dto.getMachineId()).orElse(null);
        Sensor sensor = sensorRepository.findById(dto.getSensorId()).orElse(null);

        FullReportDTO fullReportDTO = new FullReportDTO();
        fullReportDTO.setCode(dto.getCode());
        fullReportDTO.setUsername(dto.getUsername());
        fullReportDTO.setMachineName((machine!=null)?machine.getName():"-");
        fullReportDTO.setMachineType((machine!=null)?machine.getType():"-");

        fullReportDTO.setSensorName((sensor!=null)?sensor.getSensorName():"-");
        fullReportDTO.setSensorType((sensor!=null)?sensor.getSensorType():"-");
        fullReportDTO.setOpcServer((sensor!=null)?sensor.getOpcUaServer():"-");
        fullReportDTO.setNodeName((sensor!=null)?sensor.getNodeId():"-");

        fullReportDTO.setReportDescription(dto.getReportDescription());
        fullReportDTO.setReportDate(dateFormat(dto.getReportDate().getTime()));

        fullReportDTO.setImageData(dto.getImageData());
        return fullReportDTO;
    }

    private String dateFormat(long timestamp) {

        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return dateTime.format(formatter);
    }

}
