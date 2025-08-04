package com.dti.encomendas.service;

import com.dti.encomendas.model.Pedido;
import com.dti.encomendas.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public void save(Pedido pedido) { pedidoRepository.save(pedido); }

}
