package com.dti.encomendas.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Localizacao {
    private int x;
    private int y;

    public Localizacao() {}
}
