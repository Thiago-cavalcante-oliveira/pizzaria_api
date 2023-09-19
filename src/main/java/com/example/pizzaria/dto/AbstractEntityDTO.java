package com.example.pizzaria.dto;


import lombok.Getter;
import lombok.Setter;

public abstract class AbstractEntityDTO {
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private boolean ativo;

}
