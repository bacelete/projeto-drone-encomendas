package com.dti.encomendas.controller;

import com.dti.encomendas.dto.PedidoDTO;
import com.dti.encomendas.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<List<Pedido>> criarPedidos(@RequestBody PedidoDTO pedidoDTO) {
        
    }
}
