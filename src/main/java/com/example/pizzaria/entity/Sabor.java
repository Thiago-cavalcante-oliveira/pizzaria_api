package com.example.pizzaria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tb_sabores", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Sabor extends AbstractEntity {
    @Column(name = "nm_sabor")
    private String nomeSabor;
    @Column(name = "ds_ingredientes")
    private String ingredientes;
    @Column(name = "vl_sabor")

    private double valor;
}
