package com.example.pizzaria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_sabores", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sabor extends AbstractEntity {
    @Column(name = "nm_sabor")
    private String nome_sabor;
    @Column(name = "ds_ingredientes")
    private String ingredientes;
    @Column(name = "vl_sabor")
    private double valor;
}
