package com.example.pizzaria.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PizzaDTO extends AbstractEntityDTO {
    static final String MESSAGEVALUE = "O valor não pode ser negativo";

    static final String MESSAGENOTNULL = "é obrigatório";

    static final String MESSAGENOTBLANK = "não pode ser vazio";

    @NotNull(message = "O campo tipo "+ MESSAGENOTBLANK)
    private PizzaTipoDTO tipo;

    @NotNull(message = "O campo sabor "+ MESSAGENOTBLANK)
    private List<SaborDTO> sabor;

    @NotNull(message = "O campo valor "+ MESSAGENOTBLANK)
    //@NotBlank(message = "O campo valor "+ MESSAGENOTNULL)
    @Min(value = 0, message = MESSAGEVALUE)
    private Double valorPizza;


}
