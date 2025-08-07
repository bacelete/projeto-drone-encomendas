package com.dti.encomendas.service;

import com.dti.encomendas.model.Drone;
import com.dti.encomendas.model.Entrega;
import com.dti.encomendas.repository.EntregaRepository;
import com.dti.encomendas.utils.Calculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioService {
    @Autowired
    private EntregaService entregaService;

    @Autowired
    private DroneService droneService;

    public int calculaQuantidadeEntregas() {
        return entregaService.getAllEntregas().size();
    }

    public Drone getDroneMaisEficiente() {
        List<Entrega> entregas = entregaService.getAllEntregas();
        Map<Drone, Long> mapDroneEficiencia = new HashMap<>();

        for (Entrega entrega : entregas) {
            Drone drone = entrega.getDrone();
            long eficiencia = Calculo.calcularEficienciaDrone(entrega.getQuantidade_pedidos(), entrega.getDuracao_ms());
            mapDroneEficiencia.put(drone, eficiencia);
        }

        Drone droneMaisEficiente = null;


        

        return droneMaisEficiente;
    }
}
