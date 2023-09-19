package com.example.pizzaria.dto;

import jakarta.persistence.Column;
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



    @Size(min = 3, max = 150, message = "O nome deve ter entre 3 e 150 caracteres")
    private String nome;
    @Size(min = 3, max = 250, message = "O tipo deve ter entre 3 e 250 caracteres")
    private String tipo;
    @Min(value = 0, message = "O valor não pode ser negativo")
    private int quantidade;
    @Min(value = 0, message = "O valor não pode ser negativo")
    private double preco;
}
