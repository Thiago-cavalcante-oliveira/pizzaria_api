package com.example.pizzaria;

import com.example.pizzaria.dto.PizzaDTO;
import com.example.pizzaria.controller.PizzaController;
import com.example.pizzaria.entity.Pizza;
import com.example.pizzaria.repository.PizzaRepository;
import com.example.pizzaria.service.PizzaService;
import com.example.pizzaria.service.SaborService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;



@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PizzaTeste {
    @MockBean
    PizzaRepository pizzaRepository;
    @Autowired
    PizzaController pizzaController;
    @Autowired
    PizzaService pizzaService;
    @Autowired
    SaborService saborService;

   static ModelMapper modelMapper = new ModelMapper();

     SaborTeste saboresTeste = new SaborTeste();
     PizzaTipoTeste pizzaTipoTeste = new PizzaTipoTeste();

    protected static Pizza criaPizza() {
        Pizza pizza = new Pizza();
        pizza.setId(1L);
        pizza.setSabor(SaborTeste.listaSabores());
        pizza.setTipo(PizzaTipoTeste.criaPizzaTipo());
        return pizza;
    }
     protected static PizzaDTO criaPizzaDTO(Pizza pizza) {
        PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO = modelMapper.map(pizza, PizzaDTO.class);
        return pizzaDTO;
    }
     protected static List<Pizza> pizzas() {
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(criaPizza());
        return pizzas;
    }
    @BeforeEach
    void injectData() {
        Mockito.when(pizzaRepository.findById(criaPizza().getId())).thenReturn(java.util.Optional.of(criaPizza()));
        Mockito.when(pizzaRepository.existsById(criaPizza().getId())).thenReturn(true);
        Mockito.when(pizzaRepository.findAll()).thenReturn(pizzas());
        Mockito.when(pizzaRepository.save(criaPizza())).thenReturn(criaPizza());
        Mockito.when(pizzaRepository.checaID(criaPizza().getId())).thenReturn(criaPizza());
        Mockito.when(pizzaRepository.checaID(criaPizza().getId())).thenReturn(criaPizza());
    }
    @Test
    public void Teste1_FindByID() {
        var pizza = pizzaController.findById(1L);
        Assertions.assertEquals(1, pizza.getBody().getId(), 0);
    }
    @Test
     void teste2_FindAll() {
        var pizzas = pizzaController.findAll();
        Assert.assertEquals(1, pizzas.getBody().size(), 0);
    }
    @Test
     void teste3_Cadastrar() {
        PizzaDTO pizzaDTO= criaPizzaDTO(criaPizza());
        var pizza = pizzaController.cadastrar(pizzaDTO);
        Assert.assertEquals("Pizza cadastrada com sucesso", pizza.getBody());
    }
    @Test
     void teste4_Editar() {
        PizzaDTO pizzaDTO= criaPizzaDTO(criaPizza());
        var pizza = pizzaController.editar(1L, pizzaDTO);
        Assert.assertEquals("Pizza editada com sucesso", pizza.getBody());
    }
}
