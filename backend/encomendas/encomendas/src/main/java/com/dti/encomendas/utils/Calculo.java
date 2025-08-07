package com.dti.encomendas.utils;

import com.dti.encomendas.dto.PedidoDTO;
import com.dti.encomendas.model.Drone;

import java.util.List;
import java.util.Map;

public class Calculo {
    public static double calcularDistanciaEntrePontos(int x, int y) {
        return 2 * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static double calcularDistanciaTotalPedidos(Drone drone, Map<Drone, List<PedidoDTO>> entregas) {
        double distanciaTotal = 0;

        for (PedidoDTO pedidoDTO : entregas.get(drone)) {
            distanciaTotal+=pedidoDTO.getDistancia();
        }

        return distanciaTotal;
    }

    public static int calcularQuantidadePedidos(Drone drone) {
        return drone.getPedidos().size();
    }

    public static long calcularEficienciaDrone(int qtdPedidos, long duracao) {
        return qtdPedidos/duracao;
    }

}
