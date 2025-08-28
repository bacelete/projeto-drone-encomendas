package com.dti.encomendas.service;

import com.dti.encomendas.dto.DroneResponseDTO;
import com.dti.encomendas.dto.PedidoDTO;
import com.dti.encomendas.enums.StatusDrone;
import com.dti.encomendas.model.Drone;
import com.dti.encomendas.model.Pedido;
import com.dti.encomendas.repository.DroneRepository;
import com.dti.encomendas.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DroneService {
    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private TempoService tempoService;

    @Autowired
    private BateriaService bateriaService;

    public static final long VELOCIDADE_MEDIA = 80;

    public void saveAll(List<Drone> drones) {
        droneRepository.saveAll(drones);
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

    @Scheduled(fixedRate = 5000)
    public void simularBateria() {
        bateriaService.simularBateria();
    }

    public List<Drone> getDronesByStatus(StatusDrone status) {
        return droneRepository.findAllByStatus(status);
    }

    public List<DroneResponseDTO> getDronesComStatus() {
        List<Drone> drones = droneRepository.findAll();
        List<DroneResponseDTO> dronesComStatus = new ArrayList<>();

        for(Drone drone : drones) {
            DroneResponseDTO droneResponseDTO = gerarDronesComStatus(drone);
            dronesComStatus.add(droneResponseDTO);
        }

        return dronesComStatus;
    }

    private DroneResponseDTO gerarDronesComStatus(Drone drone) {
        return new DroneResponseDTO(
                drone.getId(), drone.getBateria(), drone.getStatus()
        );
    }

    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }
}
