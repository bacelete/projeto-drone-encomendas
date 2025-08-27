package com.dti.encomendas.utils;

import com.dti.encomendas.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BateriaUtil {
    @Autowired
    private DroneService droneService;

    @Scheduled(fixedRate = 5000)
    public void simularBateria() {
        droneService.simularBateriaDrone();
    }
}
