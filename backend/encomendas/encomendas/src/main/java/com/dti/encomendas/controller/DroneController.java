package com.dti.encomendas.controller;

import com.dti.encomendas.dto.DroneRequestDTO;
import com.dti.encomendas.dto.DroneResponseDTO;
import com.dti.encomendas.exception.NotFoundException;
import com.dti.encomendas.model.Drone;
import com.dti.encomendas.service.DroneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drones")
public class DroneController {
    @Autowired
    private DroneService droneService;

    @PostMapping
    public ResponseEntity<HttpStatus> criarDrone(@RequestBody @Valid List<DroneRequestDTO> dronesRequestDTO) {
        droneService.saveAll(dronesRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> criarDrone(@RequestBody @Valid DroneRequestDTO droneRequestDTO) {
        droneService.save(droneRequestDTO);
        return ResponseEntity.ok().build();
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
