package com.example.pizzaria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "Erro: o item não pode ser nulo")
@NotBlank(message = "Erro: o item não pode ser vazio")
@Data
@EqualsAndHashCode(callSuper=false)
public class ClienteDTO extends AbstractEntityDTO{
    static final String MESSAGESIZE = "Erro: e preciso ter no minimo 3 e no maximo 200 caracteres.";
    static final String MESSAGECPF = "CPF Ivalido. Informe um CPF valido.";

    @Size(min = 3, max = 200, message = MESSAGESIZE)
    private String nome;


    @Size(min = 3, max = 200, message = MESSAGESIZE)
    private String telCelular ;


    @CPF(message = MESSAGECPF)
    private String cpf;

    public void setNome(String nome) {
        if (nome == null) {
            throw new IllegalArgumentException("Nome não pode ser nulo");
        } else if (nome.equals("")) {
            throw new IllegalArgumentException("Nome não pode ser Branco");
        }
        this.nome = nome;
    }

    public void setTelCelular(String telCelular) {
        if (telCelular == null) {
            throw new IllegalArgumentException("Telefone celular não pode ser nulo");
        } else if (telCelular.equals("")) {
            throw new IllegalArgumentException("Telefone celular não pode ser Branco");
        }
        this.telCelular = telCelular;
    }



}
