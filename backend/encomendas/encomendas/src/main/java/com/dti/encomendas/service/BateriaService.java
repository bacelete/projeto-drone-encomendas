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
            int newBateria = drone.getBateria() - 5;
            drone.setBateria(newBateria);
            droneRepository.save(drone);
        }
    }

}
