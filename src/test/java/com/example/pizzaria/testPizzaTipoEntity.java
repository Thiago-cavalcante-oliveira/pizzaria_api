package com.example.pizzaria;

import com.example.pizzaria.entity.PizzaTipo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

 class TestPizzaTipoEntity {
    /*PizzaTipo pizzaTipoDTO = new PizzaTipo("Calabresa", "Grande", 50.0);

    @Test
    void testeSetNome() {
        pizzaTipoDTO.setNome("Portuguesa");
        Assertions.assertEquals("Portuguesa", pizzaTipoDTO.getNome());
    }
    @Test
    void testeGetNome() {
        pizzaTipoDTO.setNome("Calabresa");
        Assertions.assertEquals("Calabresa", pizzaTipoDTO.getNome());
    }
    @Test
    void testegetTamanho() {
        pizzaTipoDTO.setTamanho("Grande");
        Assertions.assertEquals("Grande", pizzaTipoDTO.getTamanho());
    }
    @Test
    void testeSetValor() {
        pizzaTipoDTO.setValor(50.0);
        Assertions.assertEquals(50.0, pizzaTipoDTO.getValor());
    }
    @Test
    void testeConstrutor() {
        PizzaTipo pizzaTipoDTO = new PizzaTipo();
        pizzaTipoDTO.setNome("Calabresa");
        pizzaTipoDTO.setTamanho("Grande");
        pizzaTipoDTO.setValor(50.0);
        Assertions.assertEquals("Calabresa", pizzaTipoDTO.getNome());
        Assertions.assertEquals("Grande", pizzaTipoDTO.getTamanho());
        Assertions.assertEquals(50.0, pizzaTipoDTO.getValor());
    }
    /*@Test
    void testEqualsAndHashCodeExcludesSuperclassField() {

        PizzaTipo pizzaTipoDTO1 = new PizzaTipo();
        pizzaTipoDTO1.setTamanho("Grande");
        pizzaTipoDTO1.setNome("Calabresa");
        pizzaTipoDTO1.setValor(50.0);

        PizzaTipo pizzaTipoDTO2 = new PizzaTipo();
        pizzaTipoDTO2.setValor(45.00);
        pizzaTipoDTO2.setNome("Portuguesa");
        pizzaTipoDTO2.setTamanho("Pequena");

        assertNotEquals(pizzaTipoDTO1, pizzaTipoDTO2);

        assertNotEquals(pizzaTipoDTO1.hashCode(), pizzaTipoDTO2.hashCode());
    }*/
}








