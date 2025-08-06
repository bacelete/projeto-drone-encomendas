package com.dti.encomendas.service;

import com.dti.encomendas.dto.PedidoDTO;
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

        Map<Drone, List<PedidoDTO>> mapDronePedidos = new HashMap<>();
        Map<Drone, Double> mapDronePeso = new HashMap<>();
        Map<Drone, Double> mapDroneKm = new HashMap<>();

        setarValoresIniciaisMapDrone(dronesDisponiveis, mapDronePedidos, mapDroneKm, mapDronePeso);
        ordenarPedidosPorPeso(pedidos);

        List<Pedido> entregas = alocarPedidos(pedidos, dronesDisponiveis, mapDronePedidos, mapDroneKm, mapDronePeso);

        droneService.iniciarEntregas(mapDronePedidos);
        return new PedidosResponseDTO(entregas);
    }

    private List<Drone> findDronesDisponiveis() {
        return droneService.getDroneByStatus(StatusDrone.IDLE);
    }

    private double calcularDistancia(int x, int y) {
        return Calculo.calcularDistanciaEntrePontos(x, y); //(0,0) é a base;
    }

    private void setarValoresIniciaisMapDrone(List<Drone> dronesDisponiveis,
                                      Map<Drone, List<PedidoDTO>> mapPedidos,
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
                               Map<Drone, List<PedidoDTO>> mapPedidos,
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

            double distancia = calcularDistancia(x, y);
            PedidoDTO pedidoDTO = gerarPedidoComDistancia(pedido, distancia); //pedido com distancia setada;

            for (Drone drone : drones) {
                double pesoRestante = mapPeso.get(drone);
                double kmRestante = mapKm.get(drone);

                if ((pesoPedido <= pesoRestante) && (distancia <= kmRestante)) {
                    System.out.println(pedido); //debug;
                    pedido.setDrone(drone);

                    mapPedidos.get(drone).add(pedidoDTO);
                    mapPeso.put(drone, pesoRestante - pesoPedido);
                    mapKm.put(drone, kmRestante - distancia);

                    pedidosAlocados.add(pedido);
                    break; //impede de alocar para mais de um drone;
                }

            }

        }

        if (pedidosAlocados.isEmpty()) {
            throw new NotFoundException("Não há pedidos alocados!");
        }

        pedidoRepository.saveAll(pedidosAlocados);
        return pedidosAlocados;
    }

    private PedidoDTO gerarPedidoComDistancia(Pedido pedido, double distancia) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setPeso(pedido.getPeso());
        pedidoDTO.setLocalizacao(pedido.getLocalizacao());
        pedidoDTO.setPrioridade(pedido.getPrioridade());

        pedidoDTO.setDistancia(distancia); //*IMPORTANTE

        return pedidoDTO;
    }


}
