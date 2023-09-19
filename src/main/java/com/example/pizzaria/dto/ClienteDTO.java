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
public class ClienteDTO extends AbstractEntityDTO{
    final String messageSize = "Erro: e preciso ter no minimo 3 e no maximo 200 caracteres.";
final String messageCPF = "CPF Ivalido. Informe um CPF valido.";

    @Size(min = 3, max = 200, message = messageSize)
    private String nome;


    @Size(min = 3, max = 200, message = messageSize)
    private String telCelular ;


    @CPF(message = messageCPF)
    private String cpf;
}
