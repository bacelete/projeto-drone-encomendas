package com.app.encomendas;

import com.app.encomendas.dto.PedidosResponseDTO;
import com.app.encomendas.enums.StatusDrone;
import com.app.encomendas.model.Drone;
import com.app.encomendas.model.Localizacao;
import com.app.encomendas.model.Pedido;
import com.app.encomendas.repository.PedidoRepository;
import com.app.encomendas.service.DroneService;
import com.app.encomendas.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private DroneService droneService;

    @InjectMocks
    private PedidoService pedidoService;

    private Drone dronePadrao;
    private Pedido pedidoValido;
    private Pedido pedidoPesado;
    private Pedido pedidoDistante;

    @BeforeEach
    void setUp() {
        dronePadrao = new Drone();
        dronePadrao.setId(1L);
        dronePadrao.setPesoMax(10.0);
        dronePadrao.setKmMax(50.0);
        dronePadrao.setStatus(StatusDrone.IDLE);

        pedidoValido = new Pedido();
        pedidoValido.setPeso(5.0);
        pedidoValido.setLocalizacao(new Localizacao(10, 10));

        pedidoPesado = new Pedido();
        pedidoPesado.setPeso(15.0); // > 10.0
        pedidoPesado.setLocalizacao(new Localizacao(5, 5));

        pedidoDistante = new Pedido();
        pedidoDistante.setPeso(8.0);
        pedidoDistante.setLocalizacao(new Localizacao(30, 30)); // > 50km
    }

    @Test
    void quandoHáDroneDisponivelEPedidoValido_DeveAlocarComSucesso() {
        // Arrange
        when(droneService.getDronesByStatus(StatusDrone.IDLE)).thenReturn(Collections.singletonList(dronePadrao));
        when(pedidoRepository.existsByLocalizacao_XAndLocalizacao_Y(anyInt(), anyInt())).thenReturn(false);

        ArrayList<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedidoValido);

        // Act
        PedidosResponseDTO resultado = pedidoService.saveAll(pedidos);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getPedidos_alocados().size());
        assertEquals(0, resultado.getPedidos_rejeitados().size());
        assertEquals(1L, resultado.getPedidos_alocados().get(0).getDrone().getId());
        verify(pedidoRepository, times(1)).saveAll(any());
    }

    @Test
    void quandoPedidoExcedePesoMaximo_DeveSerRejeitado() {
        // Arrange
        when(droneService.getDronesByStatus(StatusDrone.IDLE)).thenReturn(Collections.singletonList(dronePadrao));
        when(pedidoRepository.existsByLocalizacao_XAndLocalizacao_Y(anyInt(), anyInt())).thenReturn(false);

        ArrayList<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedidoPesado);

        // Act
        PedidosResponseDTO resultado = pedidoService.saveAll(pedidos);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.getPedidos_alocados().size());
        assertEquals(1, resultado.getPedidos_rejeitados().size());
    }

    @Test
    void quandoPedidoExcedeDistanciaMaxima_DeveSerRejeitado() {
        // Arrange
        when(droneService.getDronesByStatus(StatusDrone.IDLE)).thenReturn(Collections.singletonList(dronePadrao));
        when(pedidoRepository.existsByLocalizacao_XAndLocalizacao_Y(anyInt(), anyInt())).thenReturn(false);

        ArrayList<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedidoDistante);

        // Act
        PedidosResponseDTO resultado = pedidoService.saveAll(pedidos);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.getPedidos_alocados().size());
        assertEquals(1, resultado.getPedidos_rejeitados().size());
    }

    @Test
    void quandoNaoHaDronesDisponiveis_DeveLancarExcecao() {
        // Arrange
        when(droneService.getDronesByStatus(StatusDrone.IDLE)).thenReturn(new ArrayList<>());

        ArrayList<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedidoValido);

        // Act & Assert
        assertThrows(com.app.encomendas.exception.NotFoundException.class, () -> {
            pedidoService.saveAll(pedidos);
        });
    }
}