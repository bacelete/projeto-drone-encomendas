package com.app.encomendas.service;

import com.app.encomendas.dto.DroneRequestDTO;
import com.app.encomendas.dto.DroneResponseDTO;
import com.app.encomendas.dto.PedidoDTO;
import com.app.encomendas.enums.StatusDrone;
import com.app.encomendas.factory.DroneFactory;
import com.app.encomendas.model.Drone;
import com.app.encomendas.model.Pedido;
import com.app.encomendas.repository.DroneRepository;
import com.app.encomendas.repository.PedidoRepository;
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

    public void save(DroneRequestDTO droneRequestDTO) {
        Drone novo = DroneFactory.createDrone();
        novo.setBateria(droneRequestDTO.getBateria());
        novo.setKmMax(droneRequestDTO.getKmMax());
        novo.setPesoMax(droneRequestDTO.getPesoMax());
        novo.setStatus(StatusDrone.IDLE);
        droneRepository.save(novo);
    }

    public void saveAll(List<DroneRequestDTO> dronesRequestDTO) {
        List<Drone> drones = new ArrayList<>();

        for (DroneRequestDTO drone : dronesRequestDTO) {
            Drone novo = DroneFactory.createDrone();
            novo.setBateria(drone.getBateria());
            novo.setKmMax(drone.getKmMax());
            novo.setPesoMax(drone.getPesoMax());
            novo.setStatus(StatusDrone.IDLE);
            drones.add(novo);
        }

        droneRepository.saveAll(drones);
    }

    public Optional<Drone> getDroneById(Long id) {
        return droneRepository.findById(id);
    }

    public void iniciarEntregas(Map<Drone, List<PedidoDTO>> mapDronePedidos) {
        Set<Drone> dronesComPedidos = mapDronePedidos.keySet();

        for (Drone drone : dronesComPedidos) {
            List<Pedido> pedidos = pedidoRepository.findByDrone_Id(drone.getId());
            drone.setPedidos(pedidos);
        }

        //get the list of id's instead of the objects, because passing a list of ids, i can query an updated obj inside
        //an @Async method.
        List<Long> droneIds = dronesComPedidos.stream()
                .filter(drone -> !pedidoRepository.findByDrone_Id(drone.getId()).isEmpty())
                .map(Drone::getId)
                .toList();

        tempoService.gerenciarTempoDeVoo(droneIds); //passing the ids's of its drones.
    }

    @Scheduled(fixedRate = 10000)
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

}
