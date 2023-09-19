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
public class SaborDTO extends AbstractEntityDTO {

    @Size(min = 3, max = 150, message = "O nome deve ter entre 3 e 150 caracteres")
    private String nome;
    @Size(min=20, max = 1000, message="O campo ingredientes deve ter entre 20 e 1000 caracteres")
    private String ingredientes;

    @Min(value = 0, message = "O valor não pode ser negativo")
    private double valor;


}
