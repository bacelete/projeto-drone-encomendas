package com.dti.encomendas.factory;

import com.dti.encomendas.model.Drone;

public class DroneFactory {
    public static Drone createDrone() {
        return new Drone();
    }
}
