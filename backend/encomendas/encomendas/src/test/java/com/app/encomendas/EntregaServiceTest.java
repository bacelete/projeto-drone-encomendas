package com.app.encomendas;

import com.app.encomendas.model.Drone;
import com.app.encomendas.model.Entrega;
import com.app.encomendas.model.Pedido;
import com.app.encomendas.repository.EntregaRepository;
import com.app.encomendas.service.EntregaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EntregaServiceTest {

    @Mock
    private EntregaRepository entregaRepository;

    @InjectMocks
    private EntregaService entregaService;

    @Test
    void quandoCriarEntrega_DeveSalvarComDataDeInicioEQuantidade() {
        // Arrange
        Drone drone = new Drone();
        drone.setId(1L);
        drone.setPedidos(new ArrayList<>() {{ add(new Pedido()); add(new Pedido()); }}); // Drone com 2 pedidos

        // ArgumentCaptor permite "capturar" o objeto que foi passado para o método save
        ArgumentCaptor<Entrega> entregaCaptor = ArgumentCaptor.forClass(Entrega.class);

        // Act
        entregaService.criarEntrega(drone);

        // Assert
        verify(entregaRepository).save(entregaCaptor.capture()); // Captura a entrega
        Entrega entregaSalva = entregaCaptor.getValue();

        assertNotNull(entregaSalva.getInicio());
        assertEquals(1L, entregaSalva.getDrone().getId());
        assertEquals(2, entregaSalva.getQuantidade_pedidos()); // Verifica a quantidade de pedidos
        assertNull(entregaSalva.getFim());
    }

    @Test
    void quandoFinalizarEntrega_DeveSalvarComDataDeFimEDuracao() {
        // Arrange
        Entrega entrega = new Entrega();
        entrega.setInicio(LocalDateTime.now().minusMinutes(5));

        ArgumentCaptor<Entrega> entregaCaptor = ArgumentCaptor.forClass(Entrega.class);

        // Act
        entregaService.finalizarEntrega(entrega);

        // Assert
        verify(entregaRepository).save(entregaCaptor.capture());
        Entrega entregaSalva = entregaCaptor.getValue();

        assertNotNull(entregaSalva.getFim());
        assertTrue(entregaSalva.getDuracao_ms() > 0);
    }
}