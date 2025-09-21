package com.app.encomendas.controller;

import com.app.encomendas.dto.PedidosResponseDTO;
import com.app.encomendas.enums.StatusDrone;
import com.app.encomendas.exception.NotAbleException;
import com.app.encomendas.exception.NotFoundException;
import com.app.encomendas.model.Pedido;
import com.app.encomendas.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        PedidosResponseDTO pedidosResponseDTO = pedidoService.saveAll(pedidos);
        return ResponseEntity.ok(pedidosResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> getPedidos() {
        if (pedidoService.getPedidos().isEmpty()) {
            throw new NotFoundException("Não há pedidos disponíveis!");
        }
        List<Pedido> pedidos = pedidoService.getPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePedido(@PathVariable Long id) {
        if (pedidoService.findById(id).isEmpty()) {
            throw new NotFoundException("Produto não encontrado!");
        }

        Pedido pedido = pedidoService.findById(id).get();

        if (!pedido.getDrone().getStatus().toString().equals("IDLE")) {
            throw new NotAbleException("Pedido está em vôo.");
        }

        pedidoService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
