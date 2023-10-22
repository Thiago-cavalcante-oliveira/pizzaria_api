package com.example.pizzaria.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PizzaTipoDTO extends AbstractEntityDTO {
    static final String MESSAGESIZE = "Erro: e preciso ter no minimo 3 e no maximo 250 caracteres.";
    static final String MESSAGEVALUE = "O valor não pode ser negativo";
    static final String MESSAGENOTNULL = "é obrigatório";

    static final String MESSAGENOTBLANK = "não pode ser vazio";

    @NotNull(message = "O campo nome "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo nome "+ MESSAGENOTNULL)
    @Size(min = 3, max = 250, message = MESSAGESIZE)
    private String nome;

    @NotNull(message = "O campo tamanho "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo tamanho "+ MESSAGENOTNULL)
    @Size(min = 3, max = 250, message = MESSAGESIZE)
    private String tamanho;

    @NotNull(message = "O campo valor "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo valor "+ MESSAGENOTNULL)
    @Min(value = 0, message = MESSAGEVALUE)
    private double valor;

    @NotNull(message = "O campo quantiade de sabores "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo quantiade de sabores "+ MESSAGENOTNULL)
    @Min(value = 0, message = MESSAGEVALUE)
    private int qntSabores;


}
