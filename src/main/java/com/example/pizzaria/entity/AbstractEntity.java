package com.example.pizzaria.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private boolean ativo;

    @PrePersist
    public void prePersist()
    {
        this.ativo = true;
    }
}
