package com.dti.encomendas.repository;

import com.dti.encomendas.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Long, Drone> {
}
