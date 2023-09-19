package com.example.pizzaria.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_enderecos", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Endereco extends AbstractEntity {


    @Getter @Setter
    @Column(name = "tel_residencia")
    private String telResidencia;

    @Getter @Setter
    @Column(name = "nm_rua")
    private String rua;

    @Getter @Setter
    @Column(name = "nu_endereco")
    private int nuEndereco;

    @Getter @Setter
    @Column(name = "nm_bairro")
    private String bairro;

    @Getter @Setter
    @Column(name = "nu_cep")
    private String cep;

    @Getter @Setter
    @Column(name = "ds_complemento")
    private String complemento;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;


}
