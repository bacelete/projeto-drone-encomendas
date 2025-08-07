package com.dti.encomendas.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DroneRequestDTO {
    @Positive(message = "O peso máximo deve ser um valor positivo")
    private double pesoMax;
    @Positive(message = "O alcance máximo deve ser um valor positivo")
    private double kmMax;
    @Min(value = 0)
    @Max(value = 100)
    private int bateria;

    public DroneRequestDTO() {
    }
}
