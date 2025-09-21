package com.app.encomendas.repository;

import com.app.encomendas.enums.StatusDrone;
import com.app.encomendas.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findAllByStatus(StatusDrone statusDrone);
}
