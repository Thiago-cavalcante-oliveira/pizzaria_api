package com.example.pizzaria.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_clientes", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
//@EqualsAndHashCode(callSuper=false)
public class Cliente extends AbstractEntity{

    @Column(name = "nome")
    private String nome;

    @Column(name = "tel_celular")
    private String telCelular ;

    @Column(name = "cpf")
    private String cpf;
}
