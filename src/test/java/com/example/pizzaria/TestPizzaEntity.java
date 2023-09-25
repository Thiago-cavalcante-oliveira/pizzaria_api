package com.example.pizzaria;

import com.example.pizzaria.dto.PizzaDTO;
import com.example.pizzaria.dto.SaborDTO;
import com.example.pizzaria.entity.Pizza;
import com.example.pizzaria.entity.Sabor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.example.pizzaria.TestPizza.modelMapper;

@SpringBootTest
 class TestPizzaEntity {
    Pizza pizza = TestPizza.criaPizza();
    PizzaDTO pizzaDTO = TestPizza.criaPizzaDTO(pizza);

    @Test
    void testeGetId() {
        pizza.setId(1L);
        Assertions.assertEquals(1L, pizza.getId());
    }

   /* @Test
    void testeSetSabor() {
        pizza.setSabor(TestSabor.listaSabores(new Sabor()));
        Assertions.assertEquals(TestSabor.listaSabores(new Sabor()), pizza.getSabor());
    }*/


}
