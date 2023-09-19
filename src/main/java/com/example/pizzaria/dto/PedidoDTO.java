package com.example.pizzaria.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO extends AbstractEntityDTO {


    @NotNull(message = "Cliente não pode ser nulo")
    private ClienteDTO cliente;

    @NotNull(message = "Atendente não pode ser nulo")
    private FuncionarioDTO atendente;

    @NotNull(message = "Endereco não pode ser nulo")
    private EnderecoDTO endereco;

    @NotNull(message = "Entregador não pode ser nulo")
    private FuncionarioDTO entregador;

    @NotNull(message = "Solicitação de entrega não pode ser nula")
    private boolean solicitaEntrega;

    @NotNull(message = "Pedido não pode ser nulo")
    @NotBlank(message = "Pedido não pode ser vazio")
    @Size(min = 3, max = 200, message = "Pedido precisa ter um minimo de 3 e maximo de 200 caracteres")
    private String pedido;

    private double valorTotal;

    @NotNull(message = "Forma de pagamento não pode ser nulo")
    @NotBlank(message = "Forma de pagamento não pode ser vazio")
    @Size(min = 3, max = 200, message = "Forma de pagamento precisa ter um minimo de 3 e maximo de 200 caracteres")
    private String formaPagamento;

    private boolean entrega;

    private Date dataPedido;

    private Set<PizzaDTO> pizzas;

    private Set<ProdutoDiversoDTO> produtos;

}
