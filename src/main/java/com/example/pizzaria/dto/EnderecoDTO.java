package com.example.pizzaria.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO extends AbstractEntityDTO {

    @Getter @Setter
    @NotNull(message = "Telefone não pode ser nulo")
    @NotBlank(message = "Telefone não pode ser vazio")
    private String telResidencia;

    @Getter @Setter
    @NotNull(message = "Nome da rua não pode ser nulo")
    @NotBlank(message = "Nome da não pode ser vazio")
    private String rua;

    @Getter @Setter
    @NotNull(message = "Número de endereço não pode ser nulo")
    private int nuEndereco;

    @Getter @Setter
    @NotNull(message = "Nome do bairro não pode ser nulo")
    @NotBlank(message = "Nome do bairro não pode ser vazio")
    private String bairro;

    @Getter @Setter
    @NotNull(message = "CEP não pode ser nulo")
    @NotBlank(message = "CEP não pode ser vazio")
    private String cep;

    @Getter @Setter
    private String complemento;

    @Getter @Setter
    private ClienteDTO cliente;

}
