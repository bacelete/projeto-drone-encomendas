package com.app.encomendas.controller;

import com.app.encomendas.exception.NotFoundException;
import com.app.encomendas.model.Entrega;
import com.app.encomendas.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/entregas")
public class EntregaController {
    @Autowired
    private EntregaService entregaService;

    @GetMapping
    public ResponseEntity<List<Entrega>> getAllEntregas() {
        List<Entrega> entregas = entregaService.getAllEntregas();
        if (entregas.isEmpty()) {
            throw new NotFoundException("Histórico de entregas vazio.");
        }
        return ResponseEntity.ok(entregas);
    }
}
