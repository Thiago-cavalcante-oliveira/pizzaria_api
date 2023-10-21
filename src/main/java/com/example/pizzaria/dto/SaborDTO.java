package com.example.pizzaria.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaborDTO extends AbstractEntityDTO {

    static final String MESSAGESIZE = "Valor invalido, deve ter entre 3 e 250 caracteres";
    static final String MESSAGEVALUE = "O valor não pode ser negativo";

    static final String MESSAGENOTNULL = "é obrigatório";

    static final String MESSAGENOTBLANK = "não pode ser vazio";

    @NotNull(message = "O campo nome "+ MESSAGENOTNULL)
    @NotBlank(message = "O campo nome "+ MESSAGENOTBLANK)
    @Size(min = 3, max = 150, message = MESSAGESIZE)
    private String nome;

    @NotNull(message = "O campo ingredientes "+ MESSAGENOTNULL)
    @NotBlank(message = "O campo ingredientes "+ MESSAGENOTBLANK)
    @Size(min=20, max = 1000, message= MESSAGESIZE)
    private String ingredientes;

    @NotNull(message = "O campo valor "+ MESSAGENOTNULL)
    @NotBlank(message = "O campo valor "+ MESSAGENOTBLANK)
    @Min(value = 0, message = MESSAGEVALUE)
    private double valor;



}
