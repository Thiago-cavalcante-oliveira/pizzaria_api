package com.example.pizzaria.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_clientes", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends AbstractEntity{

    @Getter @Setter
    @Column(name = "nome")
    private String nome;

    @Getter @Setter
    @Column(name = "tel_celular")
    private String telCelular ;

    @Getter @Setter
    @Column(name = "cpf")
    private String cpf;
}
