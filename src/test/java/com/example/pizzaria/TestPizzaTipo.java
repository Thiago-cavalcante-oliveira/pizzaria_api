package com.example.pizzaria;


import com.example.pizzaria.controller.PizzaTipoController;
import com.example.pizzaria.entity.PizzaTipo;
import com.example.pizzaria.repository.PizzaTipoRepository;

import com.example.pizzaria.service.PizzaTipoService;
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
import com.example.pizzaria.dto.PizzaTipoDTO;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestPizzaTipo {
    @MockBean
    PizzaTipoRepository pizzaTipoRepository;
    @Autowired
    PizzaTipoService pizzaTipoService;
    @Autowired
    PizzaTipoController pizzaTipoController;


    static ModelMapper modelMapper = new ModelMapper();

    protected static PizzaTipo criaPizzaTipo() {
        PizzaTipo pizzaTipo = new PizzaTipo();
        pizzaTipo.setId(1L);
        pizzaTipo.setTamanho("Grande");
        pizzaTipo.setNome("Calabresa");
        pizzaTipo.setValor(50.00);
        return pizzaTipo;
    }

    protected static PizzaTipoDTO criaPizzaTipoDTO(PizzaTipo pizzaTipo) {
        PizzaTipoDTO pizzaTipoDTO = new PizzaTipoDTO();
        pizzaTipoDTO = modelMapper.map(pizzaTipo, PizzaTipoDTO.class);
        return pizzaTipoDTO;
    }

    protected static List<PizzaTipo> listaPizzaTipos() {
        List<PizzaTipo> pizzaTipos = new ArrayList<>();
        pizzaTipos.add(criaPizzaTipo());
        return pizzaTipos;
    }

    @BeforeEach
    void injectDado() {
        Mockito.when(pizzaTipoRepository.existsByNome(criaPizzaTipo().getNome())).thenReturn(false);
        Mockito.when(pizzaTipoRepository.findByNome(criaPizzaTipo().getNome())).thenReturn(criaPizzaTipo());
        Mockito.when(pizzaTipoRepository.findById(criaPizzaTipo().getId())).thenReturn(Optional.of(criaPizzaTipo()));
        Mockito.when(pizzaTipoRepository.save(criaPizzaTipo())).thenReturn(criaPizzaTipo());
        Mockito.when(pizzaTipoRepository.findAll()).thenReturn(listaPizzaTipos());
    }

    @Test
    void teste1_findById() {
        var pizzatipo = this.pizzaTipoController.buscar(1L);
        Assertions.assertEquals("Grande", pizzatipo.getBody().getTamanho());
    }

    @Test
    void teste2FindByIdFail() {
        assertThrows(ResponseStatusException.class, () -> {
            pizzaTipoController.buscar(2L);
        });
    }

    @Test
    void teste3findAllSuccess() {
        var pizzatipos = pizzaTipoController.findAll();
        Assertions.assertEquals(1, pizzatipos.getBody().size(), 0);
    }

    @Test
    void teste5FindAllFailException() {
        Mockito.when(pizzaTipoRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ResponseStatusException.class, () -> pizzaTipoController.findAll());
    }
    @Test
    void teste3_cadastrar() {
        PizzaTipoDTO pizzaTipoDTO = criaPizzaTipoDTO(criaPizzaTipo());
        var pizzatipo = pizzaTipoController.cadastrar(pizzaTipoDTO);
        Assert.assertEquals("Tipo de pizza cadastrado com sucesso", pizzatipo.getBody());
    }

    @Test
    void teste4_editar() {
        PizzaTipoDTO pizzaTipoDTO = criaPizzaTipoDTO(criaPizzaTipo());
        var pizzatipo = pizzaTipoController.editar(1L, pizzaTipoDTO);
        Assert.assertEquals("Tipo de pizza editado com sucesso", pizzatipo.getBody());
    }
}
