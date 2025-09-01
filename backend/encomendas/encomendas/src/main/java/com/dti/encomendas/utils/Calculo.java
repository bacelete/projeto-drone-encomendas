package com.dti.encomendas.utils;

import com.dti.encomendas.dto.PedidoDTO;
import com.dti.encomendas.model.Drone;
import com.dti.encomendas.model.Entrega;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public class Calculo {
    public static double calcularDistanciaEntrePontos(int x, int y) {
        return 2 * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static long calcularEficienciaDrone(int qtdPedidos, long duracao) {
        return qtdPedidos/duracao;
    }

    public static long calcularTempoMedio(List<Entrega> entregas) {
        if (entregas.isEmpty()) {
            return 0;
        }

        long tempoTotal = 0;
        int qtdEntregas = entregas.size();

        for (Entrega entrega : entregas) {
            tempoTotal+=entrega.getDuracao_ms();
        }
        return tempoTotal/qtdEntregas;
    }

}
