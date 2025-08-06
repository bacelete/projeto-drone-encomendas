package com.dti.encomendas.service;

import com.dti.encomendas.dto.PedidoDTO;
import com.dti.encomendas.enums.StatusDrone;
import com.dti.encomendas.model.Drone;
import com.dti.encomendas.model.Pedido;
import com.dti.encomendas.repository.DroneRepository;
import com.dti.encomendas.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class DroneService {
    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private TempoService tempoService;

    public static final long VELOCIDADE_MEDIA = 80;

    public void save(Drone drone) {
        droneRepository.save(drone);
    }

    public Optional<Drone> getDroneById(Long id) {
        return droneRepository.findById(id);
    }

    public void iniciarEntregas(Map<Drone, List<PedidoDTO>> mapDronePedidos) {
        Set<Drone> drones = mapDronePedidos.keySet();

        for (Drone drone : drones) {
            //busca os pedidos pelo repository do pedido pois o drone.setPedidos() espera List<Pedido> e nao List<PedidoDTO>
            List<Pedido> pedidosReais = pedidoRepository.findByDrone(drone);
            drone.setPedidos(pedidosReais);
        }
        tempoService.gerenciarTempoDeVoo(mapDronePedidos);
    }


    private void simularBateriaDrone(Drone drone) { }

    public List<Drone> getDroneByStatus(StatusDrone status) {
        return droneRepository.findAllByStatus(status);
    }
}
