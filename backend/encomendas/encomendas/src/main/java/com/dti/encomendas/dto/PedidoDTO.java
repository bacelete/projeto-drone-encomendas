package com.dti.encomendas.dto;

import com.dti.encomendas.enums.PrioridadePedido;
import com.dti.encomendas.model.Localizacao;
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
