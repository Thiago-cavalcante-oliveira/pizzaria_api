package com.example.pizzaria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tb_pizzas", schema = "public")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pizza extends AbstractEntity{
    @JoinColumn(name="co_pizza_tipo")
    @ManyToOne(fetch = FetchType.EAGER)
    private PizzaTipo tipo;

    @JoinColumn(name="co_sabor")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_sabor_pizza",
            joinColumns = @JoinColumn(name = "id_pizza_fk"),
            inverseJoinColumns = @JoinColumn(name = "id_sabor_fk")
    )
    private List<Sabor> sabor;

    @Column(name = "vl_pizza")
    private Double valorPizza;

}
