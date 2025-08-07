package com.dti.encomendas.dto;

import com.dti.encomendas.enums.StatusDrone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter

public class DroneResponseDTO {
    private Long id;
    private double pesoMax;
    private double kmMax;
    private int bateria;
    private StatusDrone statusDrone;

    public DroneResponseDTO() {}
}
