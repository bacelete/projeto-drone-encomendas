package com.dti.encomendas.controller;

import com.dti.encomendas.dto.PedidosResponseDTO;
import com.dti.encomendas.exception.NotFoundException;
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
    public ResponseEntity<PedidosResponseDTO> criarPedidos(@RequestBody ArrayList<Pedido> pedidos) {
        PedidosResponseDTO pedidosResponseDTO = pedidoService.save(pedidos);
        return ResponseEntity.ok(pedidosResponseDTO);
    }
}
