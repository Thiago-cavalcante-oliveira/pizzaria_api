package com.example.pizzaria;

import com.example.pizzaria.dto.PizzaDTO;
import com.example.pizzaria.controller.PizzaController;
import com.example.pizzaria.entity.Endereco;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
 class TestPizza {
    @MockBean
    PizzaRepository pizzaRepository;
    @Autowired
    PizzaController pizzaController;
    @Autowired
    PizzaService pizzaService;
    @Autowired
    SaborService saborService;

   static ModelMapper modelMapper = new ModelMapper();

     TestSabor saboresTeste = new TestSabor();
     TestPizzaTipo testPizzaTipo = new TestPizzaTipo();

    protected static Pizza criaPizza() {
        Pizza pizza = new Pizza();
        pizza.setId(1L);
        pizza.setSabor(TestSabor.listaSabores());
        pizza.setTipo(TestPizzaTipo.criaPizzaTipo());
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
     void Teste1FindByIDController() {
        var pizza = pizzaController.findById(1L);
        Assertions.assertEquals(1, pizza.getBody().getId(), 0);
    }
    @Test
    void Teste2FindByIDService() {
        var pizza = pizzaService.findById(1L);
        Assertions.assertEquals(1, pizza.getId(), 0);
    }

    @Test
     void teste3FindAllController() {
        var pizzas = pizzaController.findAll();
        Assert.assertEquals(1, pizzas.getBody().size(), 0);
    }
    @Test
    void teste4FindAllService() {
        var pizzas = pizzaService.findAll();
        Assert.assertEquals(1, pizzas.size(), 0);
    }
    @Test
     void teste5CadastrarController() {
        PizzaDTO pizzaDTO= criaPizzaDTO(criaPizza());
        var pizza = pizzaController.cadastrar(pizzaDTO);
        Assert.assertEquals("Pizza cadastrada com sucesso", pizza.getBody());
    }

    @Test
    void teste6CadastrarService() {
        PizzaDTO pizzaDTO= criaPizzaDTO(criaPizza());
        var pizza = pizzaService.cadastrar(pizzaDTO);
        Assert.assertEquals("Pizza cadastrada com sucesso", pizza);
    }


    @Test
     void teste7EditarController() {
        PizzaDTO pizzaDTO= criaPizzaDTO(criaPizza());
        var pizza = pizzaController.editar(1L, pizzaDTO);
        Assert.assertEquals("Pizza editada com sucesso", pizza.getBody());
    }

    @Test
    void teste8EditarService() {
        PizzaDTO pizzaDTO= criaPizzaDTO(criaPizza());
        var pizza = pizzaService.editar(pizzaDTO, 1L);
        Assert.assertEquals("Pizza editada com sucesso", pizza);
    }

    @Test
    void Teste9DeletarController(){
        var pizza  = pizzaController.deletar(1l);
        Assert.assertEquals("Pizza deletada com sucesso",pizza.getBody());
    }

    @Test
    void Teste10DeletarService(){
        var pizza  = pizzaService.deletar(1l);
        Assert.assertEquals("Pizza deletada com sucesso",pizza);
    }

    @Test
    void teste11findById_fail() {
        Mockito.when(pizzaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assert.assertThrows(ResponseStatusException.class, () -> {
            pizzaController.findById(5L);
        });
    }

    @Test
    void teste12CadastrarFail(){
        Mockito.when(pizzaRepository.save(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            pizzaController.cadastrar(criaPizzaDTO(criaPizza()));
        });
    }
@Test
    void teste13DeleteFail(){
        Mockito.when(pizzaRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            pizzaController.deletar(1L);
        });
}
}
