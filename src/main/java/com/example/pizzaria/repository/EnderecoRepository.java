package com.example.pizzaria.repository;

import com.example.pizzaria.entity.Cliente;
import com.example.pizzaria.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("select exists (select e from Endereco e where e.id = :id)")
    boolean doesExist(@Param("id") final Long id);



}
