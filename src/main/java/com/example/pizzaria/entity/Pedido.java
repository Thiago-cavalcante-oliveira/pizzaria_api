package com.example.pizzaria.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_pedidos", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Pedido extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    @Getter @Setter
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_funcionario_atendente", referencedColumnName = "id")
    @Getter @Setter
    private Funcionario atendente;

    @ManyToOne
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    @Getter @Setter
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "id_funcionario_entregador", referencedColumnName = "id")
    @Getter @Setter
    private Funcionario entregador;

    @Column(name = "st_solicita_entrega")
    @Getter @Setter
    private boolean solicitaEntrega;

    @Column(name = "st_pedido")
    @Getter @Setter
    private String pedido;

    @Column(name = "nu_valor_total")
    @Getter @Setter
    private double valorTotal;

    @Column(name = "tp_forma_pagamento")
    @Getter @Setter
    private String formaPagamento;

    @Column(name = "st_entrega")
    @Getter @Setter
    private boolean entrega;

    @Column(name = "dt_pedido")
    @Getter @Setter
    private Date dataPedido;


    @ManyToMany
    @JoinTable(
            name = "tb_pizzas_pedido",
            joinColumns = @JoinColumn(name = "id_pedido_fk"),
            inverseJoinColumns = @JoinColumn(name = "id_pizza_fk")
    )
    @Getter @Setter
    private Set<Pizza> pizzas = new HashSet<Pizza>();

    @ManyToMany
    @JoinTable(
            name = "tb_produtos_pedido",
            joinColumns = @JoinColumn(name = "id_pedido_fk"),
            inverseJoinColumns = @JoinColumn(name = "id_produto_fk")
    )
    @Getter @Setter
    private Set<ProdutoDiverso> produtos = new HashSet<ProdutoDiverso>();


}
