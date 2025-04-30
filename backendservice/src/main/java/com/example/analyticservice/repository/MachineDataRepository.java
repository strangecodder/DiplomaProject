package com.example.analyticservice.repository;

import com.example.analyticservice.entity.Machine;
import com.example.analyticservice.entity.MachineData;
import com.example.analyticservice.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachineDataRepository extends JpaRepository<MachineData, Integer> {
    Long countMachineDataBySensorId(Sensor sensorId);

    List<MachineData> findAllBySensorId(Sensor sensor);

}
