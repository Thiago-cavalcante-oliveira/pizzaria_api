package com.example.pizzaria.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_funcionarios", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Funcionario extends AbstractEntity{


    @Column(name = "nm_funcionario")
    private String nome;

    @Column(name = "nu_cpf_funcionario")
    private String cpf;

    @Column(name = "ds_funcao")
    private String funcao;
}
