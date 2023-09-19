package com.example.pizzaria.repository;

import com.example.pizzaria.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Query("select exists (select f from Funcionario f where f.cpf = :cpf)")
    boolean alreadyExists(@Param("cpf") final String cpf);

    @Query("select f.id from Funcionario f where f.cpf = :cpf")
    Long isTheSame(@Param("cpf") final String cpf);

    @Query("select exists (select f from Funcionario f where f.id = :id)")
    boolean doesExist(@Param("id") final Long id);
}
