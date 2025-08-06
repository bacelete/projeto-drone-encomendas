package com.dti.encomendas.model;

import com.dti.encomendas.enums.StatusDrone;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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

    public Drone() {}

    @Override
    public String toString() {
        return "ID: "+id+
                "| Peso Max: "+pesoMax+
                "| Km Max: "+kmMax+
                "| Bateria: "+bateria+"%"+
                "| Status: "+status;
    }

}
