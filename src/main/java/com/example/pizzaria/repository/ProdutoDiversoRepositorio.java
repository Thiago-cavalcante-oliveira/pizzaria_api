package com.example.pizzaria.repository;

import com.example.pizzaria.entity.ProdutoDiverso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoDiversoRepositorio extends JpaRepository<ProdutoDiverso, Long>{

    @Query("select exists (select p from ProdutoDiverso p where p.nome = :nome)")
    boolean alreadyExists(@Param("nome") final String nome);

    @Query("select p.id from ProdutoDiverso p where p.nome = :nome")
    Long isTheSame(@Param("nome") final String nome);

}
