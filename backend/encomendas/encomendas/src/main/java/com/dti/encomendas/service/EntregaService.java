package com.dti.encomendas.service;

import com.dti.encomendas.model.Drone;
import com.dti.encomendas.model.Entrega;
import com.dti.encomendas.repository.EntregaRepository;
import com.dti.encomendas.repository.PedidoRepository;
import com.dti.encomendas.utils.Calculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntregaService {
    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public void save(Entrega entrega) { entregaRepository.save(entrega); }

    public void finalizarEntrega(Entrega entrega) {
        entrega.setFim(LocalDateTime.now());
        Duration duration = Duration.between(entrega.getInicio(), entrega.getFim());
        entrega.setDuracao_ms(duration.toMillis());
        entregaRepository.save(entrega);
    }

    @Transactional
    public Entrega criarEntrega(Drone drone) {
        Entrega entrega = new Entrega();
        entrega.setDrone(drone);
        entrega.setInicio(LocalDateTime.now());

        int quantidadeDePedidos = pedidoRepository.findByDrone_Id(drone.getId()).size();
        entrega.setQuantidade_pedidos(quantidadeDePedidos);
        save(entrega);
        return entrega;
    }

    public List<Entrega> getAllEntregas() { return entregaRepository.findAll(); }

}
