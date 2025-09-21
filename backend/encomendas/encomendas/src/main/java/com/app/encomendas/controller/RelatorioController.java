package com.app.encomendas.controller;

import com.app.encomendas.dto.RelatorioDTO;
import com.app.encomendas.service.DroneService;
import com.app.encomendas.service.EntregaService;
import com.app.encomendas.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {
    @Autowired
    private EntregaService entregaService;

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private DroneService droneService;

    @GetMapping
    public ResponseEntity<RelatorioDTO> getRelatorio() {
        RelatorioDTO relatorio = new RelatorioDTO();
        relatorio.setQuantidadeEntregas(relatorioService.calculaQuantidadeEntregas());
        relatorio.setDroneMaisEficiente(relatorioService.getDroneMaisEficiente());
        relatorio.setTempoMedioPorEntrega(relatorioService.getTempoMedio());

        return ResponseEntity.ok(relatorio);
    }
}
