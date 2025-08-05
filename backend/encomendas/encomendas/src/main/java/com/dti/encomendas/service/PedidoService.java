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

        System.out.println();

        for (Drone drone : dronesDisponiveis) {
            //salva os estados em um map;
            mapDronePedidos.put(drone, new ArrayList<>());
            mapDroneKm.put(drone, drone.getKmMax()); //comeca com o kmMax;
            mapDronePeso.put(drone, drone.getPesoMax()); //comeca com o pesoMax
        }

        for (Pedido pedido : pedidos) {
            System.out.println("[INFO] Verificando pedido...");

            int x = pedido.getLocalizacao().getX();
            int y = pedido.getLocalizacao().getY();

            if (pedidoRepository.existsByLocalizacao_XAndLocalizacao_y(x, y)) {
                throw new ExistsLocalizacaoException("Localização já existente!");
            }

            double distancia = calculaDistancia(x, y);

            for (Drone drone : dronesDisponiveis) {
                List<Pedido> pedidosAlocados = mapDronePedidos.get(drone);

                double pesoRestante = mapDronePeso.get(drone);
                double kmRestante = mapDroneKm.get(drone);
                
                if (pedido.getPeso() <= pesoRestante && (distancia <= kmRestante)) {
                    pedido.setDrone(drone);
                    pedidosAlocados.add(pedido);

                    mapDronePeso.put(drone, pesoRestante - pedido.getPeso());
                    mapDroneKm.put(drone, kmRestante - distancia);
                    mapDronePedidos.put(drone, pedidosAlocados); //atualiza os pedidos no map, ele substitui o valor;

                    pedidoRepository.save(pedido);
                }

            }
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

    private double calculaDistancia(int x, int y) {
        return 2 * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); //(0,0) é a base;
    }

}
