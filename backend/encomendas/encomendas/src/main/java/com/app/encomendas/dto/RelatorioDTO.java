package com.app.encomendas.dto;

import com.app.encomendas.model.Drone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RelatorioDTO {
    private int quantidadeEntregas;
    private long tempoMedioPorEntrega;
    private DroneResponseDTO droneMaisEficiente;

    public RelatorioDTO() {}
}
