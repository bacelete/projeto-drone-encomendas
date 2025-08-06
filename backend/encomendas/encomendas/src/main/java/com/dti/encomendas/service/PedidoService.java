package com.dti.encomendas.service;

import com.dti.encomendas.dto.PedidosResponseDTO;
import com.dti.encomendas.enums.StatusDrone;
import com.dti.encomendas.exception.ExistsLocalizacaoException;
import com.dti.encomendas.exception.NotFoundException;
import com.dti.encomendas.model.Drone;
import com.dti.encomendas.model.Pedido;
import com.dti.encomendas.repository.PedidoRepository;
import com.dti.encomendas.utils.Calculo;
import com.dti.encomendas.utils.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DroneService droneService;

    public PedidosResponseDTO save(ArrayList<Pedido> pedidos) {
        List<Drone> dronesDisponiveis = findDronesDisponiveis();

        if (dronesDisponiveis.isEmpty()) {
            throw new NotFoundException("Não há drones disponíveis!");
        }

        dronesDisponiveis.forEach(drone -> System.out.println(drone.toString())); //debug;

        Map<Drone, List<Pedido>> mapDronePedidos = new HashMap<>();
        Map<Drone, Double> mapDronePeso = new HashMap<>();
        Map<Drone, Double> mapDroneKm = new HashMap<>();

        setarValoresIniciaisMapDrone(dronesDisponiveis, mapDronePedidos, mapDroneKm, mapDronePeso);
        ordenarPedidosPorPeso(pedidos);

        List<Pedido> entregas = alocarPedidos(pedidos, dronesDisponiveis, mapDronePedidos, mapDroneKm, mapDronePeso);

        droneService.iniciarEntregas(dronesDisponiveis, mapDronePedidos);
        return new PedidosResponseDTO(entregas);
    }

    private List<Drone> findDronesDisponiveis() {
        return droneService.getDroneByStatus(StatusDrone.IDLE);
    }

    private double calcularDistancia(int x, int y) {
        return Calculo.calcularDistanciaEntrePontos(x, y); //(0,0) é a base;
    }

    private void setarValoresIniciaisMapDrone(List<Drone> dronesDisponiveis,
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

    public void ordenarPedidosPorPeso(List<Pedido> pedidos) {
        Sort.ordenarPedidosPorPeso(pedidos);
    }

    private List<Pedido> alocarPedidos(List<Pedido> pedidos, List<Drone> drones,
                               Map<Drone, List<Pedido>> mapPedidos,
                               Map<Drone, Double> mapKm,
                               Map<Drone, Double> mapPeso) {
        List<Pedido> pedidosAlocados = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            System.out.println(pedido); //debug;

            int x = pedido.getLocalizacao().getX();
            int y = pedido.getLocalizacao().getY();
            double pesoPedido = pedido.getPeso();

            if (pedidoRepository.existsByLocalizacao_XAndLocalizacao_y(x, y)) {
                throw new ExistsLocalizacaoException("Localização já existente!");
            }

            double distanciaPedido = calcularDistancia(x, y);
            System.out.println("Distância do pedido: "+distanciaPedido);

            for (Drone drone : drones) {
                double pesoRestante = mapPeso.get(drone);
                double kmRestante = mapKm.get(drone);

                System.out.println(kmRestante);

                if ((pesoPedido <= pesoRestante) && (distanciaPedido <= kmRestante)) {
                    System.out.println(pedido); //debug;
                    pedido.setDrone(drone);

                    mapPedidos.get(drone).add(pedido);
                    mapPeso.put(drone, pesoRestante - pesoPedido);
                    mapKm.put(drone, kmRestante - distanciaPedido);

                    pedidosAlocados.add(pedido);
                    break; //impede de alocar para mais de um drone;
                }

            }

        }

        pedidoRepository.saveAll(pedidosAlocados);
        return pedidosAlocados;
    }


}
