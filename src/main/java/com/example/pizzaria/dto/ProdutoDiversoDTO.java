package com.example.pizzaria.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NotNull(message = "O campo nome é obrigatório")
@NotBlank(message = "O campo nome não pode ser vazio")
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper=false)
public class ProdutoDiversoDTO extends AbstractEntityDTO{

    static final String MESSAGESIZE = "Valor invalido, deve ter entre 3 e 250 caracteres";
    static final String MESSAGEQTD = "O valor não pode ser negativo";

    @Size(min = 3, max = 150, message = MESSAGESIZE)
    private String nome;
    @Size(min = 3, max = 250, message = MESSAGESIZE)
    private String tipo;
    @Min(value = 0, message = MESSAGEQTD)
    private int quantidade;
    @Min(value = 0, message = MESSAGEQTD)
    private double preco;
}
