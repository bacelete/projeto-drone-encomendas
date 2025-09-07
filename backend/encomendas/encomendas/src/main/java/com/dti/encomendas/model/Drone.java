package com.dti.encomendas.model;

import com.dti.encomendas.enums.StatusDrone;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(mappedBy = "drone")
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "drone")
    private List<Entrega> entregas;

    public Drone() {
    }

    public Drone(@Min(value = 0) @Max(value = 100) int bateria, @Positive(message = "O peso máximo deve ser um valor positivo") double pesoMax, @Positive(message = "O alcance máximo deve ser um valor positivo") double kmMax, StatusDrone statusDrone) {
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "| Peso Max: " + pesoMax +
                "| Km Max: " + kmMax +
                "| Bateria: " + bateria + "%" +
                "| Status: " + status;
    }

}
