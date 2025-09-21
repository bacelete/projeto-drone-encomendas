package com.app.encomendas.dto;

import com.app.encomendas.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PedidosResponseDTO {
    private List<Pedido> pedidos_alocados;
    private List<Pedido> pedidos_rejeitados;

    public PedidosResponseDTO() {}
}
