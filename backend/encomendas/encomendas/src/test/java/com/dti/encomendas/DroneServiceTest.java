package com.dti.encomendas;

import com.dti.encomendas.dto.DroneResponseDTO;
import com.dti.encomendas.enums.StatusDrone;
import com.dti.encomendas.model.Drone;
import com.dti.encomendas.repository.DroneRepository;
import com.dti.encomendas.service.DroneService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DroneServiceTest {

    @Mock
    private DroneRepository droneRepository;

    @InjectMocks
    private DroneService droneService;

    @Test
    void quandoGetDroneById_DeveRetornarDroneCorreto() {
        // Arrange
        Drone drone = new Drone();
        drone.setId(1L);
        when(droneRepository.findById(1L)).thenReturn(Optional.of(drone));

        // Act
        Optional<Drone> resultado = droneService.getDroneById(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void quandoGetDronesByStatus_DeveRetornarListaFiltrada() {
        // Arrange
        Drone droneIdle = new Drone();
        droneIdle.setStatus(StatusDrone.IDLE);
        Drone droneEmVoo = new Drone();
        droneEmVoo.setStatus(StatusDrone.EM_VOO);

        when(droneRepository.findAllByStatus(StatusDrone.IDLE)).thenReturn(Arrays.asList(droneIdle));

        // Act
        List<Drone> resultado = droneService.getDronesByStatus(StatusDrone.IDLE);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals(StatusDrone.IDLE, resultado.get(0).getStatus());
    }

    @Test
    void quandoGetDronesComStatus_DeveRetornarListaDeDTOs() {
        // Arrange
        Drone drone1 = new Drone();
        drone1.setId(1L);
        drone1.setBateria(100);
        drone1.setStatus(StatusDrone.IDLE);

        Drone drone2 = new Drone();
        drone2.setId(2L);
        drone2.setBateria(50);
        drone2.setStatus(StatusDrone.EM_VOO);

        when(droneRepository.findAll()).thenReturn(Arrays.asList(drone1, drone2));

        // Act
        List<DroneResponseDTO> resultado = droneService.getDronesComStatus();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals(StatusDrone.IDLE, resultado.get(0).getStatusDrone());
        assertEquals(2L, resultado.get(1).getId());
        assertEquals(StatusDrone.EM_VOO, resultado.get(1).getStatusDrone());
    }
}