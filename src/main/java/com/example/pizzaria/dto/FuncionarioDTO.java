package com.example.pizzaria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO extends AbstractEntityDTO{


    @Getter @Setter
    @NotNull(message = "Nome não pode ser nulo")
    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    @Getter @Setter
    @NotNull(message = "CPF não pode ser nulo")
    @NotBlank(message = "CPF não pode ser vazio")
    @CPF(message = "CPF deve seguir o formato XXX.XXX.XXX-XX")
    private String cpf;

    @Getter @Setter
    @NotNull(message = "Função não pode ser nula")
    @NotBlank(message = "Função não pode ser vazia")
    private String funcao;
}
