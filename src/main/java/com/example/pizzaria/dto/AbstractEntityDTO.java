package com.example.pizzaria.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractEntityDTO {
    private Long id;
    private boolean ativo;

}
