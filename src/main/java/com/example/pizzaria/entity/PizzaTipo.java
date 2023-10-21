package com.example.pizzaria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tb_tipo_pizzas", schema = "public")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PizzaTipo extends AbstractEntity{
    @Column(name = "nm_tipo_pizza")
    private String nome;
    @Column(name = "tm_pizza")
    private String tamanho;
    @Column(name = "vl_tm_pizza")
    private double valor;
}
