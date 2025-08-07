package com.dti.encomendas.dto;

import com.dti.encomendas.model.Drone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RelatorioDTO {
    private int quantidadeEntregas;
    private long tempoMedioPorEntrega;
    private Drone droneMaisEficiente;

    public RelatorioDTO() {}
}
