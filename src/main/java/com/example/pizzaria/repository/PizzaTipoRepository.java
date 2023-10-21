package com.example.pizzaria.repository;

import com.example.pizzaria.entity.PizzaTipo;
import com.example.pizzaria.entity.Sabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PizzaTipoRepository extends JpaRepository<PizzaTipo, Long> {

    @Query("select count(*)>0 from PizzaTipo pizzaTipo where pizzaTipo.nome = :nome")
    public boolean existsByNome(String nome);

    @Query("SELECT pizzaTipo FROM PizzaTipo pizzaTipo where pizzaTipo.nome = :nome")
    public PizzaTipo findByNome(String nome);

    @Query ("SELECT EXISTS(SELECT p FROM Pizza p WHERE p.tipo.id = :idPizzaTipo)")
    public boolean pizzaTipoExistTb_pizza(Long idPizzaTipo);

    @Query("select exists (select p from PizzaTipo p where p.nome = :nome)")
    boolean alreadyExists(@Param("nome") final String nome);
}
