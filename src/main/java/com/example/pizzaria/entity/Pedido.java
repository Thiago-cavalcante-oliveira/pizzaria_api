package com.example.pizzaria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_pedidos", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pedido extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_funcionario_atendente", referencedColumnName = "id")
    private Funcionario atendente;

    @ManyToOne
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "id_funcionario_entregador", referencedColumnName = "id")
    private Funcionario entregador;

    @Column(name = "st_solicita_entrega")
    private boolean solicitaEntrega;

    @Column(name = "st_pedido")
    private String situacaoPedido;

    @Column(name = "nu_valor_total")
    private double valorTotal;

    @Column(name = "tp_forma_pagamento")
    private String formaPagamento;

    @Column(name = "st_entrega")
    private boolean entrega;

    @Column(name = "dt_pedido")
    private Date dataPedido;


    @ManyToMany
    @JoinTable(
            name = "tb_pizzas_pedido",
            joinColumns = @JoinColumn(name = "id_pedido_fk"),
            inverseJoinColumns = @JoinColumn(name = "id_pizza_fk")
    )
    @Getter @Setter
    private Set<Pizza> pizzas = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tb_produtos_pedido",
            joinColumns = @JoinColumn(name = "id_pedido_fk"),
            inverseJoinColumns = @JoinColumn(name = "id_produto_fk")
    )
    private Set<ProdutoDiverso> produtos = new HashSet<>();


}
