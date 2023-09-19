package com.example.pizzaria.repository;

import com.example.pizzaria.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("select exists (select c from Cliente c where c.cpf = :cpf)")
    boolean alreadyExists(@Param("cpf") final String cpf);

    @Query("select c.id from Cliente c where c.cpf = :cpf")
    Long isTheSame(@Param("cpf") final String cpf);

    @Query("select exists (select c from Cliente c where c.id = :id)")
    boolean doesExist(@Param("id") final Long id);
}
