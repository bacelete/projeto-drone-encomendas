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

    public Optional<Pedido> findById(Long id) { return pedidoRepository.findById(id); }

    public List<Pedido> getPedidos() { return pedidoRepository.findAll(); }

    public void deleteById(Long id) { pedidoRepository.deleteById(id);}

    public PedidosResponseDTO saveAll(ArrayList<Pedido> pedidos) {
        List<Drone> dronesDisponiveis = findDronesDisponiveis();

        if (dronesDisponiveis.isEmpty()) {
            throw new NotFoundException("Não há drones disponíveis!");
        }

        Map<Drone, List<PedidoDTO>> mapDronePedidos = new HashMap<>();
        Map<Drone, Double> mapDronePeso = new HashMap<>();
        Map<Drone, Double> mapDroneKm = new HashMap<>();

        setarValoresIniciaisMapDrone(dronesDisponiveis, mapDronePedidos, mapDroneKm, mapDronePeso);
        ordenarPedidosPorPeso(pedidos);
        PedidosResponseDTO pedidosResponseDTO = alocarPedidos(pedidos, dronesDisponiveis, mapDronePedidos, mapDroneKm, mapDronePeso);

        return pedidosResponseDTO;
    }

    private List<Drone> findDronesDisponiveis() {
        return droneService.getDronesByStatus(StatusDrone.IDLE);
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

    private PedidosResponseDTO alocarPedidos(List<Pedido> pedidos, List<Drone> drones,
                               Map<Drone, List<PedidoDTO>> mapPedidos,
                               Map<Drone, Double> mapKm,
                               Map<Drone, Double> mapPeso) {
        List<Pedido> alocados = new ArrayList<>();
        List<Pedido> rejeitados = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            boolean foiAlocado = false;

            int x = pedido.getLocalizacao().getX();
            int y = pedido.getLocalizacao().getY();

            double pesoPedido = pedido.getPeso();

            if (pedidoRepository.existsByLocalizacao_XAndLocalizacao_Y(x, y)) {
                throw new ExistsLocalizacaoException("Localização já existente!");
            }

            double distancia = calcularDistancia(x, y);
            PedidoDTO pedidoDTO = gerarPedidoComDistancia(pedido, distancia); //order with distance calculated;

            for (Drone drone : drones) {
                double pesoRestante = mapPeso.get(drone);
                double kmRestante = mapKm.get(drone);

                //check if order satisfies the drone conditions;
                if ((pesoPedido <= pesoRestante) && (distancia <= kmRestante)) {
                    pedido.setDrone(drone);

                    mapPedidos.get(drone).add(pedidoDTO);
                    mapPeso.put(drone, pesoRestante - pesoPedido);
                    mapKm.put(drone, kmRestante - distancia);

                    alocados.add(pedido);
                    foiAlocado = true; //control variable to add inside "rejeitados" or "alocados" list;
                    break; //avoid to allocate the order to more than one drone.
                }
            }

            if (!foiAlocado) {
                rejeitados.add(pedido);
            }
        }

        if (!alocados.isEmpty()) {
            pedidoRepository.saveAll(alocados);
        }

        droneService.iniciarEntregas(mapPedidos);
        return new PedidosResponseDTO(alocados, rejeitados);
    }

    private PedidoDTO gerarPedidoComDistancia(Pedido pedido, double distancia) {
        return new PedidoDTO(
                pedido.getId(),
                pedido.getPeso(),
                pedido.getPrioridade(),
                pedido.getLocalizacao(),
                distancia
        );
    }


}
