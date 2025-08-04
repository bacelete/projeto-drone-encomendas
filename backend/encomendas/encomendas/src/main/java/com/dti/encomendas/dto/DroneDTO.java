package com.dti.encomendas.dto;

import com.dti.encomendas.enums.StatusDrone;
import com.dti.encomendas.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DroneDTO {
    private Long id;
    private double pesoMax;
    private double kmMax;
    private int bateria;
    private List<Pedido> pedidos;

    public DroneDTO() {}
}
