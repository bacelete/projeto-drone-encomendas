package com.dti.encomendas.service;

import com.dti.encomendas.dto.PedidoDTO;
import com.dti.encomendas.enums.StatusDrone;
import com.dti.encomendas.exception.NotFoundException;
import com.dti.encomendas.model.Drone;
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

    @Async
    public void gerenciarTempoDeVoo(Map<Drone, List<PedidoDTO>> entregas)
    {
        Set<Drone> drones = entregas.keySet();

        if (drones.isEmpty()) {
            throw new NotFoundException("Não há drones alocados para entregas!");
        }

        for (Drone drone : drones) {
            drone.setInicio(LocalDateTime.now());
            drone.setStatus(StatusDrone.EM_VOO);
            droneRepository.save(drone);

            double distanciaTotal = Calculo.calcularDistanciaTotalPedidos(drone, entregas);
            long tempoEstimado = calcularTempoTotalEntrega(distanciaTotal);

            System.out.println("Distância total dos pedidos desse drone: "+distanciaTotal);
            System.out.println("Tempo estimado dos pedidos desse drone: "+tempoEstimado);

            try {
                Thread.sleep(tempoEstimado);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }

            drone.setFim(LocalDateTime.now());
            drone.setStatus(StatusDrone.IDLE);
            droneRepository.save(drone);
        }

    }

    private long calcularTempoTotalEntrega(double distancia) {
        long velocidadeEmMS = (long) (VELOCIDADE_MEDIA/3.6);
        double tempoEstimado = distancia/velocidadeEmMS; //em seg;
        return (long) tempoEstimado * 1000;
    }
}
