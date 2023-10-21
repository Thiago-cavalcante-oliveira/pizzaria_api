package com.example.pizzaria.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDiversoDTO extends AbstractEntityDTO{

    static final String MESSAGESIZE = "Valor invalido, deve ter entre 3 e 250 caracteres";
    static final String MESSAGEQTD = "O valor não pode ser negativo";

    static final String MESSAGENOTNULL = "é obrigatório";

    static final String MESSAGENOTBLANK = "não pode ser vazio";

    @NotNull(message = "O campo nome "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo nome "+ MESSAGENOTNULL)
    @Size(min = 3, max = 150, message = MESSAGESIZE)
    private String nome;

    @NotNull(message = "O campo tipo "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo tipo "+ MESSAGENOTNULL)
    @Size(min = 3, max = 250, message = MESSAGESIZE)
    private String tipo;

    @NotNull(message = "O campo quantidade "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo quantidade "+ MESSAGENOTNULL)
    @Min(value = 0, message = MESSAGEQTD)
    private int quantidade;

    @NotNull(message = "O campo preço "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo preço "+ MESSAGENOTNULL)
    @Min(value = 0, message = MESSAGEQTD)
    private double preco;
}
