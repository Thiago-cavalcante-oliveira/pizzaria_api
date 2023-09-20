package com.example.pizzaria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
@Data
@NotNull(message = "Nome não pode ser nulo")
@NotBlank(message = "Nome não pode ser vazio")
public class FuncionarioDTO extends AbstractEntityDTO {
    static final String MESSAGECPF = "CPF Ivalido. Informe um CPF valido.";
    private String nome;
    @CPF(message = MESSAGECPF)
    private String cpf;
    private String funcao;
}
