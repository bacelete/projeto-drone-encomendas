package com.dti.encomendas.model;

import com.dti.encomendas.enums.PrioridadePedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double peso;
    private PrioridadePedido prioridade;

    @Embedded
    private Localizacao localizacao; 

    public Pedido() {}
}
