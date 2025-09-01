package com.dti.encomendas.service;

import com.dti.encomendas.dto.PedidoDTO;
import com.dti.encomendas.enums.StatusDrone;
import com.dti.encomendas.exception.NotFoundException;
import com.dti.encomendas.model.Drone;
import com.dti.encomendas.model.Entrega;
import com.dti.encomendas.repository.DroneRepository;
import com.dti.encomendas.utils.Calculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.dti.encomendas.service.DroneService.VELOCIDADE_MEDIA;

@Service
public class TempoService {
    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private EntregaService entregaService;

    @Async
    public void gerenciarTempoDeVoo(Map<Drone, List<PedidoDTO>> pedidos)
    {
        Set<Drone> dronesComPedidos = pedidos.keySet();

        if (dronesComPedidos.isEmpty()) {
            throw new NotFoundException("Não há drones alocados para entregas!");
        }

        for (Drone drone : dronesComPedidos) {

            Drone droneAtualizado = droneRepository.findById(drone.getId()).get();

            Entrega entrega = entregaService.criarEntrega(droneAtualizado);
            droneAtualizado.setStatus(StatusDrone.EM_VOO);
            droneRepository.save(droneAtualizado);

            try {
                Thread.sleep(30000);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }

            entregarPedido(droneAtualizado);
            finalizarVoo(droneAtualizado, entrega);
        }

    }

    private void entregarPedido(Drone drone) {
        System.out.println("Drone: "+drone.getId()+" entregando o pedido...");
        drone.setStatus(StatusDrone.ENTREGANDO);
        droneRepository.save(drone);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void finalizarVoo(Drone drone, Entrega entrega) {
        System.out.println("Drone: "+drone.getId()+" entregou o pedido com sucesso!");
        drone.setStatus(StatusDrone.IDLE);

        droneRepository.save(drone);
        entregaService.finalizarEntrega(entrega);
    }

}
