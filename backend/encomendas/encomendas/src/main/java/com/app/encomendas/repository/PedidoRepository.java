package com.app.encomendas.repository;

import com.app.encomendas.model.Drone;
import com.app.encomendas.model.Localizacao;
import com.app.encomendas.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    boolean existsByLocalizacao_XAndLocalizacao_Y(int x, int y);
    List<Pedido> findByDrone_Id(Long id);
}
