package com.example.pizzaria.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_enderecos", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
//@EqualsAndHashCode(callSuper=false)
public class Endereco extends AbstractEntity {


    @Column(name = "tel_residencia")
    private String telResidencia;

    @Column(name = "nm_rua")
    private String rua;

    @Column(name = "nu_endereco")
    private int nuEndereco;

    @Column(name = "nm_bairro")
    private String bairro;

    @Column(name = "nu_cep")
    private String cep;

    @Column(name = "ds_complemento")
    private String complemento;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;



}
