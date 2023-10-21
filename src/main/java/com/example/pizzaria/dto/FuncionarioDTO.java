package com.example.pizzaria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter

public class FuncionarioDTO extends AbstractEntityDTO {
    static final String MESSAGECPF = "CPF Ivalido. Informe um CPF valido.";

    static final String MESSAGENOTNULL = "é obrigatório";

    static final String MESSAGENOTBLANK = "não pode ser vazio";

    @NotNull(message = "O campo nome "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo nome "+ MESSAGENOTNULL)
    private String nome;
    @CPF(message = MESSAGECPF)
    @NotNull(message = "O campo CPF "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo CPF "+ MESSAGENOTNULL)
    private String cpf;
    @NotNull(message = "O campo função "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo função "+ MESSAGENOTNULL)
    private String funcao;
}
