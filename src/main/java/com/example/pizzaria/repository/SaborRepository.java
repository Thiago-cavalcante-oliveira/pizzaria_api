package com.example.pizzaria.repository;

import com.example.pizzaria.entity.Sabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SaborRepository extends JpaRepository<Sabor, Long> {

    @Query("select count(*)>0 from Sabor sabor where sabor.nome = :nome")
    boolean existsByNome(String nome);
    @Query("SELECT sabor FROM Sabor sabor where sabor.nome = :nome")
    Sabor findByNome(String nome);

    @Query(value = "SELECT EXISTS (SELECT p FROM tb_sabor_pizza p WHERE p.id_sabor_fk = :id)", nativeQuery = true)
    boolean existsInPedido(@Param("id") final Long id);

    @Query("select exists (select s from Sabor s where s.nome = :nome)")
    boolean alreadyExists(@Param("nome") final String nome);

}
