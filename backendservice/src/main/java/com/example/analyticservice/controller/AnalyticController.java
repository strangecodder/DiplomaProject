package com.example.analyticservice.controller;

import com.example.analyticservice.dto.ChartDTO;
import com.example.analyticservice.dto.MachinesAnalyticDTO;
import com.example.analyticservice.entity.MachineData;
import com.example.analyticservice.entity.Sensor;
import com.example.analyticservice.service.AnalyticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/analytic")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AnalyticController {

    /*
    *     public List<MachinesAnalyticDTO> getMachinesBySensorId() {
        return  machineRepository.findAll().stream()
                .map(machine -> new MachinesAnalyticDTO(
                        machine.getName(),
                        machine.getType(),
                        machineSensorRepository.countSensorsByMachine(machine),
                        getLogsCount(machine.getMachineId())
                )).
                toList();
    }
    * */

    private final AnalyticService analyticService;

    @GetMapping("/getMachines")
    public List<MachinesAnalyticDTO> getMachines() {
        return analyticService.getMachinesBySensorId();
    }

    @GetMapping("/getMachine")
    public void getMachine(@RequestParam("machineId") Long machineId) {

    }

    @GetMapping("/getAllLogs")
    public List<MachineData> getAllLogs(@RequestParam Long machineId) {
        return analyticService.getLogsByMachineId(machineId);
    }

    @GetMapping("/getSensors")
    public List<Sensor> getSensors(@RequestParam Long machineId) {
        return analyticService.getSensorsByMachineId(machineId);
    }

    @PostMapping("/setupChart")
    public List<MachineData> getChartData(@RequestBody ChartDTO chartDTO) {
        return analyticService.getChartData(chartDTO);
    }
}
