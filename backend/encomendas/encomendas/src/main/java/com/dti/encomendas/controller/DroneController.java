package com.dti.encomendas.controller;

import com.dti.encomendas.dto.DroneDTO;
import com.dti.encomendas.enums.StatusDrone;
import com.dti.encomendas.model.Drone;
import com.dti.encomendas.repository.DroneRepository;
import com.dti.encomendas.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drones")
public class DroneController {
    @Autowired
    private DroneService droneService;

    @PostMapping("/criar")
    public ResponseEntity<Drone> criarDrone(@RequestBody DroneDTO droneDTO) {
        Drone novo = new Drone();

        novo.setBateria(droneDTO.getBateria());
        novo.setKmMax(droneDTO.getKmMax());
        novo.setPesoMax(droneDTO.getPesoMax());
        novo.setStatus(StatusDrone.IDLE);

        droneService.save(novo);
        return ResponseEntity.ok(novo);
    }
}
