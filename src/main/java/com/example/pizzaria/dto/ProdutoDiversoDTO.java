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
public class ProdutoDiversoDTO extends AbstractEntityDTO{

    final String messageSize = "Valor invalido, deve ter entre 3 e 250 caracteres";
    final String messageQtd = "O valor não pode ser negativo";

    @Size(min = 3, max = 150, message = messageSize)
    private String nome;
    @Size(min = 3, max = 250, message = messageSize)
    private String tipo;
    @Min(value = 0, message = messageQtd)
    private int quantidade;
    @Min(value = 0, message = messageQtd)
    private double preco;
}
