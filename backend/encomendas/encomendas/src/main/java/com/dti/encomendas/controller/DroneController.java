package com.dti.encomendas.controller;

import com.dti.encomendas.dto.DroneRequestDTO;
import com.dti.encomendas.dto.DroneResponseDTO;
import com.dti.encomendas.enums.StatusDrone;
import com.dti.encomendas.exception.NotFoundException;
import com.dti.encomendas.model.Drone;
import com.dti.encomendas.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drones")
public class DroneController {
    @Autowired
    private DroneService droneService;

    @PostMapping("/criar")
    public ResponseEntity<Drone> criarDrone(@RequestBody DroneRequestDTO droneRequestDTO) {
        Drone novo = new Drone();

        novo.setBateria(droneRequestDTO.getBateria());
        novo.setKmMax(droneRequestDTO.getKmMax());
        novo.setPesoMax(droneRequestDTO.getPesoMax());
        novo.setStatus(StatusDrone.IDLE);

        droneService.save(novo);
        return ResponseEntity.ok(novo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drone> getDrone(@PathVariable Long id) {
        if (droneService.getDroneById(id).isEmpty()) {
            throw new NotFoundException("Drone não encontrado");
        }
        Drone drone = droneService.getDroneById(id).get();
        return ResponseEntity.ok(drone);
    }

    @GetMapping("/status")
    public ResponseEntity<List<DroneResponseDTO>> getDrones() {
        if (droneService.getDronesComStatus().isEmpty()) {
            throw new NotFoundException("Não há drones disponíveis.");
        }
        List<DroneResponseDTO> drones = droneService.getDronesComStatus();
        return ResponseEntity.ok(drones);
    }
}
