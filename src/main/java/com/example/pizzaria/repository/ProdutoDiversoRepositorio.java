package com.example.pizzaria.repository;

import com.example.pizzaria.entity.ProdutoDiverso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoDiversoRepositorio extends JpaRepository<ProdutoDiverso, Long>{

    @Query("select exists (select p from ProdutoDiverso p where p.tipo = :tipo)")
    boolean alreadyExists(@Param("tipo") final String tipo);

    @Query("select p.id from ProdutoDiverso p where p.tipo = :tipo")
    Long isTheSame(@Param("tipo") final String tipo);

}
