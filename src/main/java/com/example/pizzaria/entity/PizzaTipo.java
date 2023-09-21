package com.example.pizzaria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_tipo_pizzas", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PizzaTipo extends AbstractEntity{
@Column(name = "nm_tipo_pizza")
    private String nome;
@Column(name = "tm_pizza")
    private String tamanho;
@Column(name = "vl_tm_pizza")
    private double valor;
}
