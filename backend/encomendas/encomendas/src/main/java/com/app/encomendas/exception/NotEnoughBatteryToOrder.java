package com.app.encomendas.exception;

public class NotEnoughBatteryToOrder extends RuntimeException {
    public NotEnoughBatteryToOrder(String message) {
        super(message);
    }
}
