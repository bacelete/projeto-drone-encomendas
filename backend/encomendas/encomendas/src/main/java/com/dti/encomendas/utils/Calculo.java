package com.dti.encomendas.utils;

public class Calculo {
    public static double calcularDistanciaEntrePontos(int x, int y) {
        return 2 * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}
