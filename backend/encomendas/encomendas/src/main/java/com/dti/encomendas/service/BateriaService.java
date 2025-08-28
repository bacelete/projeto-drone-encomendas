package com.dti.encomendas.service;

import com.dti.encomendas.model.Drone;
import com.dti.encomendas.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BateriaService {
    @Autowired
    private DroneRepository droneRepository;

    public void simularBateria() {
        List<Drone> drones = droneRepository.findAll();

        for (Drone drone : drones) {
            if (drone.getBateria() > 0) {
                int newBateria = drone.getBateria() - getConsumptionRate(drone.getStatus().toString());
                drone.setBateria(newBateria);
                droneRepository.save(drone);
            }
        }
    }
    private int getConsumptionRate(String status) {
        switch (status) {
            case "IDLE": return 2;
            case "EM_VOO": return 5;
            case "ENTREGANDO": return 3;
            default: return 0;
        }
    }

}
