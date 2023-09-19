package com.example.pizzaria.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NotNull(message = "O campo nome é obrigatório")
@NotBlank(message = "O campo nome não pode ser vazio")
@NoArgsConstructor
@AllArgsConstructor
public class PizzaTipoDTO extends AbstractEntityDTO {
    final String messageSize = "Erro: e preciso ter no minimo 3 e no maximo 250 caracteres.";
    final String messageValue = "O valor não pode ser negativo";

    @Size(min = 3, max = 250, message = messageSize)
    private String nome;

    @Size(min = 3, max = 250, message = messageSize)
    private String tamanho;

    @Min(value = 0, message = messageValue)
    private double valor;


}
