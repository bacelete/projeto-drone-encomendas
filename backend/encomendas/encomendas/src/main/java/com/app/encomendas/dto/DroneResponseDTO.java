package com.app.encomendas.dto;

import com.app.encomendas.enums.StatusDrone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter

public class DroneResponseDTO {
    private Long id;
    private int bateria;
    private StatusDrone statusDrone;

    public DroneResponseDTO() {}
}
