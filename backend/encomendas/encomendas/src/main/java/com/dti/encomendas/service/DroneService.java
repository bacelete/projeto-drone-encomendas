package com.dti.encomendas.service;

import com.dti.encomendas.model.Drone;
import com.dti.encomendas.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DroneService {
    @Autowired
    private DroneRepository droneRepository;

    public void save(Drone drone) { droneRepository.save(drone); }
    public Optional<Drone> getDroneById(Long id) { return droneRepository.findById(id); }
}
