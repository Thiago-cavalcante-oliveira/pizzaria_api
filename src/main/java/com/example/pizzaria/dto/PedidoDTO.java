package com.example.pizzaria.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO extends AbstractEntityDTO {
    static final String MESSAGESIZE = "Erro: e preciso ter no minimo 3 e no maximo 200 caracteres.";

    static final String MESSAGENOTNULL = "é obrigatório";

    static final String MESSAGENOTBLANK = "não pode ser vazio";


    private ClienteDTO cliente;

    private FuncionarioDTO atendente;

    private EnderecoDTO endereco;

    private FuncionarioDTO entregador;

    private boolean solicitaEntrega;


    @NotNull(message = "O campo pedido "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo pedido "+ MESSAGENOTNULL)
    @Size(min = 3, max = 200, message = MESSAGESIZE)
    private String situacaoPedido;

    private double valorTotal;

    @Size(min = 3, max = 200, message = MESSAGESIZE)

    @NotNull(message = "O campo forma de pagamento "+ MESSAGENOTBLANK)
    @NotBlank(message = "O campo forma de pagamento "+ MESSAGENOTNULL)
    private String formaPagamento;

    private boolean entrega;

    private Date dataPedido;

    private List<PizzaDTO> pizzas;

    private List<ProdutoDiversoDTO> produtos;

}
