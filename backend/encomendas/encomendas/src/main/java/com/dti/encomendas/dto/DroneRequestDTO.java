package com.dti.encomendas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DroneRequestDTO {
    private double pesoMax;
    private double kmMax;
    private int bateria;

    public DroneRequestDTO() {}
}
