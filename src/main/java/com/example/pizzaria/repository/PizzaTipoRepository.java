package com.example.pizzaria.repository;

import com.example.pizzaria.entity.PizzaTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PizzaTipoRepository extends JpaRepository<PizzaTipo, Long> {

    @Query("select count(*)>0 from PizzaTipo pizzaTipo where pizzaTipo.nome = :nome")
    public boolean existsByNome(String nome);

    @Query("SELECT pizzaTipo FROM PizzaTipo pizzaTipo where pizzaTipo.nome = :nome")
    public PizzaTipo findByNome(String nome);

    @Query ("SELECT count(*)>0 FROM Pizza pizza where pizza.tipo = :idPizzaTipo")
    public boolean pizzaTipoExistTb_pizza(Long idPizzaTipo);
}
