package com.dti.encomendas.exception;

public class AboveDroneCapacity extends RuntimeException {
    public AboveDroneCapacity(String message) {
        super(message);
    }
}
