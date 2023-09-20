package com.example.pizzaria.repository;

import com.example.pizzaria.entity.Sabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaborRepository extends JpaRepository<Sabor, Long> {

    @Query("select count(*)>0 from Sabor sabor where sabor.nome_sabor = :nome")
    public boolean existsByNome(String nome);
    @Query("SELECT sabor FROM Sabor sabor where sabor.nome_sabor = :nome")
    public Sabor findByNome(String nome);

    @Query ("SELECT count(*)>0 FROM Pizza pizza where pizza.sabor = :idSabor")
    public boolean saborExistTb_pizza(Long idSabor);

}
