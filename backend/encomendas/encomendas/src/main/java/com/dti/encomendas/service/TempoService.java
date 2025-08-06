package com.dti.encomendas.service;

import com.dti.encomendas.dto.PedidoDTO;
import com.dti.encomendas.enums.StatusDrone;
import com.dti.encomendas.model.Drone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.dti.encomendas.service.DroneService.VELOCIDADE_MEDIA;

@Service
public class TempoService {
    @Autowired
    private DroneService droneService;

    @Async
    public void gerenciarTempoDeVoo(Drone drone, Map<Drone, List<PedidoDTO>> entregas)
    {
        double distanciaTotal = 0;

        drone.setInicio(LocalDateTime.now());
        drone.setStatus(StatusDrone.EM_VOO);
        droneService.save(drone);

        for (PedidoDTO pedidoDTO : entregas.get(drone)) {
            distanciaTotal+=pedidoDTO.getDistancia();
        }

        long tempoEstimado = calcularTempoTotalEntrega(distanciaTotal);

        try {
            Thread.sleep(tempoEstimado);
        }
        catch(InterruptedException e) {
            //...
        }

        drone.setStatus(StatusDrone.IDLE);
        drone.setFim(LocalDateTime.now());
        droneService.save(drone);
    }

    private long calcularTempoTotalEntrega(double distancia) {
        return (long) (distancia/VELOCIDADE_MEDIA) * 3600 * 1000; //converte para ms
    }
}
