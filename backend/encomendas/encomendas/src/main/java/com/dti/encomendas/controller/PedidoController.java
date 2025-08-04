package com.dti.encomendas.controller;

import com.dti.encomendas.model.Pedido;
import com.dti.encomendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        Pedido novo = new Pedido();
        novo.setPeso(pedido.getPeso());
        novo.setPrioridade(pedido.getPrioridade());
        novo.setLocalizacao(pedido.getLocalizacao());

        pedidoService.save(novo);
        return ResponseEntity.ok(novo);
    }
}
