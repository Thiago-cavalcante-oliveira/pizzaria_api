package com.example.pizzaria.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NotNull(message = "O item não pode ser nulo")
@NotBlank(message = "O item não pode ser vazio")

public class PedidoDTO extends AbstractEntityDTO {
    static final String messageSize = "Erro: e preciso ter no minimo 3 e no maximo 200 caracteres.";



    private ClienteDTO cliente;

    private FuncionarioDTO atendente;

    private EnderecoDTO endereco;

    private FuncionarioDTO entregador;

    private boolean solicitaEntrega;

    @Size(min = 3, max = 200, message = messageSize)
    private String pedido;

    private double valorTotal;

    @Size(min = 3, max = 200, message = messageSize)
    private String formaPagamento;

    private boolean entrega;

    private Date dataPedido;

    private Set<PizzaDTO> pizzas;

    private Set<ProdutoDiversoDTO> produtos;

}
