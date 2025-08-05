package com.dti.encomendas.repository;

import com.dti.encomendas.enums.StatusDrone;
import com.dti.encomendas.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findAllByStatus(StatusDrone statusDrone);
}
