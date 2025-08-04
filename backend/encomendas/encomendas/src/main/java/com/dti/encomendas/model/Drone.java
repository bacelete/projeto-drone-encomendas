package com.dti.encomendas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double pesoMax;
    private double kmMax;

    @Min(value = 0)
    @Max(value = 100)
    private int bateria;

    @Enumerated(EnumType.STRING)
    private StatusDrone status;

    public Drone() {}

}
