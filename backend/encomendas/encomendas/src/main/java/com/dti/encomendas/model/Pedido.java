package com.dti.encomendas.model;

import com.dti.encomendas.enums.PrioridadePedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double peso;

    @Enumerated(EnumType.STRING)
    private PrioridadePedido prioridade;

    @Embedded
    private Localizacao localizacao;

    @ManyToOne
    @JoinColumn(name="id_drone", nullable = false)
    @JsonIgnore
    private Drone drone;

    @Override
    public String toString() {
        return "ID: "+id+
                "| Peso: "+peso+
                "| Prioridade: "+prioridade+
                "| Localizacao: ("+localizacao.getX()+", "+localizacao.getY()+")";
    }

    public Pedido() {}
}
