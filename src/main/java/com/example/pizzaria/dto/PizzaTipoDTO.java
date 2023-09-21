package com.example.pizzaria.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NotNull(message = "O campo nome é obrigatório")
@NotBlank(message = "O campo nome não pode ser vazio")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PizzaTipoDTO extends AbstractEntityDTO {
    static final String MESSAGESIZE = "Erro: e preciso ter no minimo 3 e no maximo 250 caracteres.";
    static final String MESSAGEVALUE = "O valor não pode ser negativo";

    @Size(min = 3, max = 250, message = MESSAGESIZE)
    private String nome;

    @Size(min = 3, max = 250, message = MESSAGESIZE)
    private String tamanho;

    @Min(value = 0, message = MESSAGEVALUE)
    private double valor;


}
