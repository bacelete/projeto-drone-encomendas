package com.app.encomendas.model;

import com.app.encomendas.enums.PrioridadePedido;
import com.app.encomendas.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "O peso do pedido deve ser um valor positivo.")
    private double peso;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "A prioridade não pode ser nula.")
    private PrioridadePedido prioridade;

    @NotNull(message = "A localização não pode ser nula.")
    @Valid
    @Embedded
    private Localizacao localizacao;

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido = StatusPedido.AGUARDANDO;

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
