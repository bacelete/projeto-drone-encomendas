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
import org.springframework.transaction.annotation.Transactional;

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
            List<Pedido> pedidosParaEntrega = pedidos.stream()
                    .filter(pedido -> pedido.getStatusPedido().equals(StatusPedido.ENVIADO)).toList();

            if (!pedidosParaEntrega.isEmpty()) { //precisa adicionar a verificacao de status pro pedido;
                System.out.println(pedidos);
                Entrega entrega = entregaService.criarEntrega(drone);

                drone.setStatus(StatusDrone.EM_VOO);
                droneRepository.save(drone);

                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                entregarPedido(drone, entrega);
            }

        }

    }

    @Transactional
    private void entregarPedido(Drone drone, Entrega entrega) {
        Drone droneAtualizado = droneRepository.findById(drone.getId()).orElse(drone); //getting the latest drone

        droneAtualizado.setStatus(StatusDrone.ENTREGANDO);
        droneRepository.save(droneAtualizado);

        List<Pedido> pedidos = pedidoRepository.findByDrone_Id(drone.getId()); //fixing the lazy error
        Pedido pedido = pedidos.stream()
                .filter((p) -> p.getStatusPedido().equals(StatusPedido.ENVIADO))
                .findFirst().
                orElse(null);

        if (pedido != null) {
            System.out.println("Antes de salvar pedido: Drone está = " + pedido.getDrone().getStatus());

            pedido.setStatusPedido(StatusPedido.ENTREGUE);
            pedidoRepository.save(pedido);
            System.out.println("Depois de salvar pedido: Drone está = " + pedido.getDrone().getStatus());

        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        finalizarVoo(drone, entrega);
    }

    @Transactional
    private void finalizarVoo(Drone drone, Entrega entrega) {
        entregaService.finalizarEntrega(entrega);

        Drone droneAtualizado = droneRepository.findById(drone.getId()).orElse(drone);
        droneAtualizado.setStatus(StatusDrone.IDLE);

        System.out.println("Salvando drone como IDLE: " + droneAtualizado.getId());
        droneRepository.saveAndFlush(droneAtualizado);
    }

}
