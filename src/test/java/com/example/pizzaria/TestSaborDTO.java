package com.example.pizzaria;

import com.example.pizzaria.dto.SaborDTO;
import com.example.pizzaria.entity.Sabor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestSaborDTO {


    Sabor sabor = new Sabor();

    @Test
    void testeGetId() {
        sabor.setId(1L);
        Assertions.assertEquals(1L, sabor.getId());
    }

    @Test
    void testeSetId() {
        sabor.setId(5L);
        Assertions.assertEquals(5L, sabor.getId());
    }

    @Test
    void testeGetNomeSabor() {
        sabor.setNomeSabor("Calabresa");
        Assertions.assertEquals("Calabresa", sabor.getNomeSabor());
    }

    @Test
    void testeSetSabor() {
        sabor.setNomeSabor("Portuguesa");
        Assertions.assertEquals("Portuguesa", sabor.getNomeSabor());
    }

    @Test
    void testeGetIngredientes() {
        sabor.setIngredientes("Calabresa, queijo, molho de tomate");
        Assertions.assertEquals("Calabresa, queijo, molho de tomate", sabor.getIngredientes());
    }

    @Test
    void testeSetIngredientes() {
        sabor.setIngredientes("Calabresa, queijo, molho de tomate");
        Assertions.assertEquals("Calabresa, queijo, molho de tomate", sabor.getIngredientes());
    }

    @Test
    void testeGetValor() {
        sabor.setValor(10.00);
        Assertions.assertEquals(10.00, sabor.getValor());
    }

    @Test
    void testeSetValor() {
        sabor.setValor(50.00);
        Assertions.assertEquals(50.00, sabor.getValor());
    }

    @Test
    void testeConstrutorVazio(){
        SaborDTO sabor = new SaborDTO();
        Assertions.assertNotNull(sabor);
    }

    @Test
    void testEqualsAndHashCodeExcludesSuperclassField() {
        // Criar duas instâncias de SaborDTO com o mesmo valor para um campo herdado (se houver)
        SaborDTO sabor1 = new SaborDTO();
        sabor1.setId(1L); // Suponhamos que a classe pai (superclasse) tenha um campo "id"
        sabor1.setNome("Pizza Margherita");

        SaborDTO sabor2 = new SaborDTO();
        sabor2.setId(1L); // Mesmo valor para o campo herdado
        sabor2.setNome("Pizza Calabresa");

        // Verificar que as instâncias não são iguais
        assertNotEquals(sabor1,sabor2);

        // Verificar que os códigos hash são diferentes
        assertNotEquals(sabor1.hashCode(), sabor2.hashCode());
    }

    @Test
    void testAllArgsConstructor() {
        // Crie uma instância de SaborDTO usando o construtor gerado pelo Lombok
        SaborDTO sabor = new SaborDTO("Pizza Margherita", "Ingredientes da Margherita", 12.99);

        // Verifique se os campos foram inicializados corretamente
        assertNotNull(sabor);
        assertEquals("Pizza Margherita", sabor.getNome());
        assertEquals("Ingredientes da Margherita", sabor.getIngredientes());
        assertEquals(12.99, sabor.getValor(), 0.001); // Use uma margem de erro pequena para valores double
    }

    @Test
    public void testEquals() {
        // Criar duas instâncias de Sabor com campos iguais
        SaborDTO sabor1 = new SaborDTO("Pizza Margherita", "Ingredientes da Margherita", 12.99);
        SaborDTO sabor2 = new SaborDTO("Pizza Margherita", "Ingredientes da Margherita", 12.99);

        // Verificar se as instâncias são iguais
        assertTrue(sabor1.equals(sabor2));
        assertTrue(sabor2.equals(sabor1));

        // Criar duas instâncias de Sabor com campos diferentes
        Sabor sabor3 = new Sabor("Pizza Calabresa", "Ingredientes da Calabresa", 13.99);
        Sabor sabor4 = new Sabor("Pizza Pepperoni", "Ingredientes do Pepperoni", 11.99);

        // Verificar se as instâncias não são iguais
        assertFalse(sabor1.equals(sabor3));
        assertFalse(sabor1.equals(sabor4));
        assertFalse(sabor3.equals(sabor4));
    }
}
