package com.dti.encomendas.service;

import com.dti.encomendas.enums.StatusDrone;
import com.dti.encomendas.exception.ExistsLocalizacaoException;
import com.dti.encomendas.exception.NotFoundException;
import com.dti.encomendas.model.Drone;
import com.dti.encomendas.model.Pedido;
import com.dti.encomendas.repository.DroneRepository;
import com.dti.encomendas.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DroneRepository droneRepository;

    public void save(ArrayList<Pedido> pedidos) {
        List<Drone> dronesDisponiveis = findDronesDisponiveis();

        if (dronesDisponiveis.isEmpty()) {
            throw new NotFoundException("Não há drones disponíveis!");
        }

        dronesDisponiveis.forEach(drone -> System.out.println(drone.toString()));

        Map<Drone, List<Pedido>> mapDronePedidos = new HashMap<>();
        Map<Drone, Double> mapDronePeso = new HashMap<>();
        Map<Drone, Double> mapDroneKm = new HashMap<>();

        setarValoresMapDrone(dronesDisponiveis, mapDronePedidos, mapDronePeso, mapDroneKm);

        for (Pedido pedido : pedidos) {
            System.out.println("[INFO] Verificando pedido...");
            System.out.println(pedido.toString());

            int x = pedido.getLocalizacao().getX();
            int y = pedido.getLocalizacao().getY();

            if (pedidoRepository.existsByLocalizacao_XAndLocalizacao_y(x, y)) {
                throw new ExistsLocalizacaoException("Localização já existente!");
            }

            double distancia = calcularDistancia(x, y);

        }

        for (Drone drone : dronesDisponiveis) {
            drone.setPedidos(mapDronePedidos.get(drone));
            drone.setInicio(LocalDateTime.now());
            drone.setStatus(StatusDrone.EM_VOO);
            droneRepository.save(drone);
        }

    }

    private List<Drone> findDronesDisponiveis() {
        return droneRepository.findAllByStatus(StatusDrone.IDLE);
    }

    private double calcularDistancia(int x, int y) {
        return 2 * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); //(0,0) é a base;
    }

    private void setarValoresMapDrone(List<Drone> dronesDisponiveis,
                                         Map<Drone, List<Pedido>> mapPedidos,
                                         Map<Drone, Double> mapKm,
                                         Map<Drone, Double> mapPeso) {
        for (Drone drone : dronesDisponiveis) {
            //salva os estados em um map;
            mapPedidos.put(drone, new ArrayList<>());
            mapKm.put(drone, drone.getKmMax()); //comeca com o kmMax;
            mapPeso.put(drone, drone.getPesoMax()); //comeca com o pesoMax
        }
    }

}
