package com.example.pizzaria.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@NotBlank(message = "O campo não pode ser vazio")
@NotNull(message = "O campo não pode ser nulo")
public class PizzaDTO extends AbstractEntityDTO {
    static final String MESSAGEVALUE = "O valor não pode ser negativo";

    private PizzaTipoDTO tipoDTO;
    private List<SaborDTO> saborDTO;
    @Min(value = 0, message = MESSAGEVALUE)
    private Double valorPizza;

    public PizzaDTO(PizzaTipoDTO pizzaTipo, SaborDTO sabor) {
        super();
    }
}
