package com.app.encomendas.factory;

import com.app.encomendas.model.Drone;

public class DroneFactory {
    public static Drone createDrone() {
        return new Drone();
    }
}
