package com.dti.encomendas.service;

import com.dti.encomendas.model.Drone;
import com.dti.encomendas.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BateriaService {
    @Autowired
    private DroneRepository droneRepository;

    public void simularBateria() {
        List<Drone> drones = droneRepository.findAll();
        List<Drone> dronesAtualizados = new ArrayList<>();

        for (Drone drone : drones) {
            if (drone.getBateria() > 0) {
                int newBateria = drone.getBateria() - getConsumptionRate(drone.getStatus().toString());
                drone.setBateria(Math.max(0, newBateria));
                dronesAtualizados.add(drone);
            }
        }
        droneRepository.saveAll(dronesAtualizados);
    }

    private int getConsumptionRate(String status) {
        return switch (status) {
            case "IDLE" -> 1;
            case "EM_VOO" -> 5;
            case "ENTREGANDO" -> 3;
            default -> 0;
        };
    }

}
