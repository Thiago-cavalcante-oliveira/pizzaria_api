package com.example.pizzaria.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_pizzas", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pizza extends AbstractEntity{
    @JoinColumn(name="co_pizza_tipo")
    @OneToOne
    private PizzaTipo tipo;

    @JoinColumn(name="co_sabor")
    @OneToMany
    private List<Sabor> sabor;

    @Column(name = "vl_pizza")
    private Double valorPizza;
    public Pizza(PizzaTipo pizzaTipo, Sabor sabor) {
        super();
    }
}
