package com.app.encomendas.dto;

import com.app.encomendas.enums.PrioridadePedido;
import com.app.encomendas.model.Localizacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PedidoDTO {
    private Long id;
    private double peso;
    private PrioridadePedido prioridade;
    private Localizacao localizacao;
    private double distancia; //importante!

    public PedidoDTO() {}
}
