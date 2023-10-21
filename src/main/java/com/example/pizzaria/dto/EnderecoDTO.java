package com.example.pizzaria.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor

public class EnderecoDTO extends AbstractEntityDTO {


    static final String MESSAGENOTNULL = "é obrigatório";

    static final String MESSAGENOTBLANK = "não pode ser vazio";

    @NotNull(message = "O campo telefone "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo telefone "+ MESSAGENOTNULL)
    private String telResidencia;

    @NotNull(message = "O campo rua "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo rua "+ MESSAGENOTNULL)
    private String rua;

    @NotNull(message = "O campo numero "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo numero "+ MESSAGENOTNULL)
    private int nuEndereco;


    @NotNull(message = "O campo bairro "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo bairro "+ MESSAGENOTNULL)
    private String bairro;


    @NotNull(message = "O campo cep "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo cep "+ MESSAGENOTNULL)
    private String cep;

    private String complemento;

    @NotNull(message = "O campo cliente "+ MESSAGENOTBLANK)
    private ClienteDTO cliente;

}
