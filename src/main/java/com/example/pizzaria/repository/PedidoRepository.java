package com.example.pizzaria.repository;

import com.example.pizzaria.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("select exists (select p from Pedido p where p.id = :id)")
    boolean doesExist(@Param("id") final Long id);
}
