package com.example.pizzaria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO extends AbstractEntityDTO{

    @Getter @Setter
    @NotNull(message = "Nome não pode ser nulo")
    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 3, max = 200, message = "Nome precisa ter um minimo de 3 e maximo de 200 caracteres")
    private String nome;

    @Getter @Setter
    @NotNull(message = "Telefone não pode ser nulo")
    @NotBlank(message = "Telefone não pode ser vazio")
    @Size(min = 3, max = 200, message = "Telefone precisa ter um minimo de 3 e maximo de 200 caracteres")
    private String telCelular ;

    @Getter @Setter
    @NotNull(message = "CPF não pode ser nulo")
    @NotBlank(message = "CPF não pode ser vazio")
    @CPF(message = "CPF deve seguir o formato XXX.XXX.XXX-XX")
    private String cpf;
}
