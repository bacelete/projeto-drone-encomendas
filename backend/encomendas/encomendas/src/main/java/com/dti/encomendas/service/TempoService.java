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
    public void gerenciarTempoDeVoo(List<Long> ids)
    {
        List<Drone> drones = droneRepository.findAllById(ids);

        if (drones.isEmpty()) {
            throw new NotFoundException("Não há drones alocados para entregas!");
        }

        for (Drone drone : drones) {;
            Entrega entrega = entregaService.criarEntrega(drone);
            drone.setStatus(StatusDrone.EM_VOO);

            droneRepository.save(drone);

            try {
                Thread.sleep(30000);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }

            entregarPedido(drone);
            finalizarVoo(drone, entrega);
        }

    }

    private void entregarPedido(Drone drone) {
        drone.setStatus(StatusDrone.ENTREGANDO);
        droneRepository.save(drone);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void finalizarVoo(Drone drone, Entrega entrega) {
        drone.setStatus(StatusDrone.IDLE);

        droneRepository.save(drone);
        entregaService.finalizarEntrega(entrega);
    }

}
