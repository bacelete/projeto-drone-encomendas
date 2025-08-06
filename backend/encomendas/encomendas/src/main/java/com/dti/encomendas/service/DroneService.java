package com.dti.encomendas.service;

import com.dti.encomendas.enums.StatusDrone;
import com.dti.encomendas.model.Drone;
import com.dti.encomendas.model.Pedido;
import com.dti.encomendas.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DroneService {
    @Autowired
    private DroneRepository droneRepository;

    private static final double VELOCIDADE_MEDIA = 80;

    public void create(Drone drone) {
        droneRepository.save(drone);
    }

    public Optional<Drone> getDroneById(Long id) {
        return droneRepository.findById(id);
    }

    public void iniciarEntregas(List<Drone> drones, Map<Drone, List<Pedido>> mapDronePedidos) {
        for (Drone drone : drones) {
            drone.setPedidos(mapDronePedidos.get(drone));
            drone.setInicio(LocalDateTime.now());
            drone.setStatus(StatusDrone.EM_VOO);
            droneRepository.save(drone);
        }
    }

    private double calcularTempoTotalEntrega(double distancia) {
        return distancia/VELOCIDADE_MEDIA;
    }

    private void simularBateriaDrone(Drone drone) {
        
    }

    public List<Drone> getDroneByStatus(StatusDrone status) {
        return droneRepository.findAllByStatus(status);
    }
}
