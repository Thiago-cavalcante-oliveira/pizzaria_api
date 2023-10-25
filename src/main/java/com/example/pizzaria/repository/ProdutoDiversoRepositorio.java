package com.example.pizzaria.repository;

import com.example.pizzaria.entity.Cliente;
import com.example.pizzaria.entity.ProdutoDiverso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoDiversoRepositorio extends JpaRepository<ProdutoDiverso, Long>{

    @Query("select exists (select p from ProdutoDiverso p where p.nome = :nome)")
    boolean alreadyExists(@Param("nome") final String nome);

    @Query("select p.id from ProdutoDiverso p where p.nome = :nome")
    Long isTheSame(@Param("nome") final String nome);

    @Query(value = "SELECT EXISTS (SELECT p FROM tb_produtos_pedido p WHERE p.id_produto_fk = :id)", nativeQuery = true)
    boolean existsInPedido(@Param("id") final Long id);

    List<ProdutoDiverso> findAllByAtivoIsTrue();

}
