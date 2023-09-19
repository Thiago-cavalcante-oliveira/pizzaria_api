package com.example.pizzaria.repository;

import com.example.pizzaria.entity.ProdutoDiverso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoDiversoRepositorio extends JpaRepository<ProdutoDiverso, Long>{


}
