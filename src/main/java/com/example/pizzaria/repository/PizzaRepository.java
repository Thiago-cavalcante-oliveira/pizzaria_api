package com.example.pizzaria.repository;

import com.example.pizzaria.entity.Cliente;
import com.example.pizzaria.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    @Query("SELECT pizza FROM Pizza pizza WHERE pizza.id = :id")
    public Pizza checaID(Long id);

    @Query(value = "SELECT EXISTS (SELECT p FROM tb_pizzas_pedido p WHERE p.id_pizza_fk = :id)", nativeQuery = true)
    boolean existsInPedido(@Param("id") final Long id);

    List<Pizza> findAllByAtivoIsTrue();

}
