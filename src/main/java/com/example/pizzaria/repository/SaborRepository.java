package com.example.pizzaria.repository;

import com.example.pizzaria.entity.Sabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SaborRepository extends JpaRepository<Sabor, Long> {

    @Query("select count(*)>0 from Sabor sabor where sabor.nomeSabor = :nome")
    boolean existsByNome(String nome);
    @Query("SELECT sabor FROM Sabor sabor where sabor.nomeSabor = :nome")
    Sabor findByNome(String nome);

    /*
    @Query ("SELECT EXISTS(SELECT p FROM Pizza p WHERE :idSabor MEMBER OF p.sabor)")
    boolean saborExistTb_pizza(Long idSabor);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Pizza p WHERE :idSabor MEMBER OF p.sabor")
    boolean saborExistTb_pizza(Long idSabor);*/

    @Query("select exists (select s from Sabor s where s.nomeSabor = :nome)")
    boolean alreadyExists(@Param("nome") final String nome);

}
