package com.example.pizzaria.repository;

import com.example.pizzaria.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    @Query("SELECT pizza FROM Pizza pizza WHERE pizza.id = :id")
    public Pizza checaID(Long id);

//    @Query("SELECT count(*) FROM Pedido pedido WHERE pedido.pizzas.id = :id")
//    public boolean checaPizzaPedido(Long id);

}
