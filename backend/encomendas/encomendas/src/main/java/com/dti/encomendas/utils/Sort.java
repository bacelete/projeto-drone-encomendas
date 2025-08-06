package com.dti.encomendas.utils;

import com.dti.encomendas.model.Pedido;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sort {
    public static void ordenarPedidosPorDistancia(List<Pedido> pedidos) {
        Collections.sort(pedidos, new Comparator<Pedido>() {
            @Override
            public int compare(Pedido o1, Pedido o2) {
                if (o1.getPeso() > o2.getPeso()) {
                    return -1;
                }
                if (o1.getPeso() < o2.getPeso()) {
                    return 1;
                }
                return 0;
            }
        });
    }
}
