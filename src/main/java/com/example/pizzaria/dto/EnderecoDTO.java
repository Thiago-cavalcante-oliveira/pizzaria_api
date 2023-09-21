package com.example.pizzaria.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@NotNull(message = "O item não pode ser nulo")
@NotBlank(message = "O item não pode ser vazio")
@EqualsAndHashCode(callSuper=false)
public class EnderecoDTO extends AbstractEntityDTO {


    private String telResidencia;


    private String rua;


    private int nuEndereco;


    private String bairro;


    private String cep;

    private String complemento;

    private ClienteDTO cliente;

}
