package com.dti.encomendas.service;

import com.dti.encomendas.dto.DroneResponseDTO;
import com.dti.encomendas.model.Drone;
import com.dti.encomendas.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BateriaService {
    @Autowired
    private DroneRepository droneRepository;

    public void simularBateria(DroneResponseDTO drone) {
        if (drone.getBateria() > 0) {

        }
    }
}
