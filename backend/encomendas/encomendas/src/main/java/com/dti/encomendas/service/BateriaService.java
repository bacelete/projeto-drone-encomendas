package com.dti.encomendas.service;

import com.dti.encomendas.enums.StatusDrone;
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

    private static final int TAXA_CARREGAMENTO = 5;
    private static final int MAX_BATERIA = 100;

    public void simularBateria() {
        List<Drone> drones = droneRepository.findAll();
        List<Drone> dronesAtualizados = new ArrayList<>();

        for (Drone drone : drones) {
            if (drone.getBateria() > 0 && drone.getStatus() != StatusDrone.CARREGANDO) {
                descarregarBateria(drone);
            } else {
                carregarBateria(drone);
            }
            dronesAtualizados.add(drone);
        }
        droneRepository.saveAll(dronesAtualizados);
    }

    private void descarregarBateria(Drone drone) {
        int bateria = drone.getBateria() - getConsumptionRate(drone.getStatus().toString());
        drone.setBateria(Math.max(0, bateria));
    }

    private void carregarBateria(Drone drone) {
        drone.setStatus(StatusDrone.CARREGANDO);
        int bateria = drone.getBateria() + TAXA_CARREGAMENTO;
        if (bateria > MAX_BATERIA) {
            bateria = MAX_BATERIA;
        }

        drone.setBateria(bateria);
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
