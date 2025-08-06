package com.dti.encomendas.repository;

import com.dti.encomendas.model.Drone;
import com.dti.encomendas.model.Localizacao;
import com.dti.encomendas.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    boolean existsByLocalizacao_XAndLocalizacao_y(int x, int y);
    List<Pedido> findByDrone(Drone drone);
}
