package com.example.pizzaria.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NotBlank(message = "O campo não pode ser vazio")
@NotNull(message = "O campo não pode ser nulo")
//@EqualsAndHashCode(callSuper=false)
public class PizzaDTO extends AbstractEntityDTO {
    static final String MESSAGEVALUE = "O valor não pode ser negativo";

    private PizzaTipoDTO tipoDTO;
    private List<SaborDTO> saborDTO;
    @Min(value = 0, message = MESSAGEVALUE)
    private Double valorPizza;


}
