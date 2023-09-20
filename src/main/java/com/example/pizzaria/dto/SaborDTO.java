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
public class SaborDTO extends AbstractEntityDTO {

    static final String messageSize = "Valor invalido, deve ter entre 3 e 250 caracteres";
    static final String messageValue = "O valor não pode ser negativo";

    @Size(min = 3, max = 150, message = messageSize)
    private String nome;
    @Size(min=20, max = 1000, message=messageSize)
    private String ingredientes;

    @Min(value = 0, message = messageValue)
    private double valor;


}
