package com.example.pizzaria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ClienteDTO extends AbstractEntityDTO{
    static final String MESSAGESIZE = "Erro: e preciso ter no minimo 3 e no maximo 200 caracteres.";
    static final String MESSAGECPF = "CPF Ivalido. Informe um CPF valido.";

    static final String MESSAGENOTNULL = "é obrigatório";

    static final String MESSAGENOTBLANK = "não pode ser vazio";

    @NotNull(message = "O campo nome "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo nome "+ MESSAGENOTNULL)
    @Size(min = 3, max = 200, message = MESSAGESIZE)
    private String nome;



    @NotNull(message = "O campo celular "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo celular "+ MESSAGENOTNULL)
    @Size(min = 3, max = 200, message = MESSAGESIZE)
    private String telCelular ;


    @CPF(message = MESSAGECPF)
    @NotNull(message = "O campo CPF "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo CPF "+ MESSAGENOTNULL)
    private String cpf;

}
