package com.example.analyticservice.repository;

import com.example.analyticservice.entity.Machine;
import com.example.analyticservice.entity.MachineSensor;
import com.example.analyticservice.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface MachineSensorRepository extends JpaRepository<MachineSensor, Long> {
    List<MachineSensor> findByMachine(Machine machine);

    int countSensorsByMachine(Machine machine);

    MachineSensor findMachineSensorBySensor_OpcUaServerAndSensor_NodeId(String opcUaServer, String nodeId);

}
