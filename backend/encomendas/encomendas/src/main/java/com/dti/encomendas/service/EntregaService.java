package com.dti.encomendas.service;

import com.dti.encomendas.model.Drone;
import com.dti.encomendas.model.Entrega;
import com.dti.encomendas.repository.EntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EntregaService {
    @Autowired
    private EntregaRepository entregaRepository;

    public void save(Entrega entrega) { entregaRepository.save(entrega); }

    public void finalizarEntrega(Entrega entrega) {
        entrega.setFim(LocalDateTime.now());
        entregaRepository.save(entrega);
    }

    public Entrega criarEntrega(Drone drone) {
        Entrega entrega = new Entrega();
        entrega.setDrone(drone);
        entrega.setInicio(LocalDateTime.now());

        save(entrega);

        return entrega;
    }

}
