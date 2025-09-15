package com.dti.encomendas.service;

import com.dti.encomendas.enums.StatusDrone;
import com.dti.encomendas.enums.StatusPedido;
import com.dti.encomendas.exception.NotFoundException;
import com.dti.encomendas.model.Drone;
import com.dti.encomendas.model.Entrega;
import com.dti.encomendas.model.Pedido;
import com.dti.encomendas.repository.DroneRepository;
import com.dti.encomendas.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempoService {
    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EntregaService entregaService;

    @Autowired
    private BateriaService bateriaService;

    @Async
    public void gerenciarTempoDeVoo(List<Long> droneIds) {
        List<Drone> drones = droneRepository.findAllById(droneIds);

        if (drones.isEmpty()) {
            throw new NotFoundException("Não há drones alocados para entregas!");
        }

        for (Drone drone : drones) {
            List<Pedido> pedidos = pedidoRepository.findByDrone_Id(drone.getId());

            if (!pedidos.isEmpty()) { //precisa adicionar a verificacao de status pro pedido;
                System.out.println(pedidos.toString());
                Entrega entrega = entregaService.criarEntrega(drone);

                drone.setStatus(StatusDrone.EM_VOO);
                droneRepository.save(drone);

                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                entregarPedido(drone);
                finalizarVoo(drone, entrega);
            }

        }

    }

    private void entregarPedido(Drone drone) {
        Drone droneAtualizado = droneRepository.findById(drone.getId()).orElse(drone); //getting the latest drone

        droneAtualizado.setStatus(StatusDrone.ENTREGANDO);
        droneRepository.save(droneAtualizado);

        Pedido pedido = drone.getPedidos().stream()
                .filter((p) -> p.getStatusPedido().equals(StatusPedido.ENVIADO))
                .findFirst().
                orElse(null);

        if (pedido != null) {
            pedido.setStatusPedido(StatusPedido.ENTREGUE);
            pedidoRepository.save(pedido);
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void finalizarVoo(Drone drone, Entrega entrega) {
        Drone droneAtualizado = droneRepository.findById(drone.getId()).orElse(drone); //getting the latest drone

        droneAtualizado.setStatus(StatusDrone.IDLE);
        entregaService.finalizarEntrega(entrega);
        droneRepository.save(droneAtualizado);
    }

}
