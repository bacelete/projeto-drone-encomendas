package com.dti.encomendas.controller;

import com.dti.encomendas.dto.PedidosResponseDTO;
import com.dti.encomendas.exception.NotFoundException;
import com.dti.encomendas.model.Pedido;
import com.dti.encomendas.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidosResponseDTO> criarPedidos(@RequestBody @Valid ArrayList<Pedido> pedidos) {
        PedidosResponseDTO pedidosResponseDTO = pedidoService.save(pedidos);
        return ResponseEntity.ok(pedidosResponseDTO);
    }

    public ResponseEntity<List<Pedido>> getPedidos() {
        if (pedidoService.getPedidos().isEmpty()) {
            throw new NotFoundException("Não há pedidos disponíveis!");
        }
        List<Pedido> pedidos = pedidoService.getPedidos();
        return ResponseEntity.ok(pedidos); 
    }
}
