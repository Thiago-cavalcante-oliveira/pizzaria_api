package com.example.pizzaria;


import com.example.pizzaria.controller.PizzaTipoController;
import com.example.pizzaria.dto.PizzaDTO;
import com.example.pizzaria.entity.PizzaTipo;
import com.example.pizzaria.repository.PizzaTipoRepository;

import com.example.pizzaria.service.PizzaTipoService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentMatchers;
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
    void teste1FindByIdSuccess() {
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
    void teste4FindAllFailException() {
        Mockito.when(pizzaTipoRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ResponseStatusException.class, () -> pizzaTipoController.findAll());
    }
    @Test
    void teste5CadastrarSuccess() {
        PizzaTipoDTO pizzaTipoDTO = criaPizzaTipoDTO(criaPizzaTipo());
        var pizzatipo = pizzaTipoController.cadastrar(pizzaTipoDTO);
        Assert.assertEquals("Tipo de pizza cadastrado com sucesso", pizzatipo.getBody());
    }

    @Test
    void teste6CadastrarFail() {
        Mockito.when(pizzaTipoRepository.existsByNome(Mockito.anyString())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> pizzaTipoController.cadastrar(criaPizzaTipoDTO(criaPizzaTipo())));
        Assertions.assertFalse(exception.getMessage().contains("Sabor já cadastrado"));
    }

    @Test
    void teste7CadastrarFailCatch(){
        Mockito.when(pizzaTipoRepository.save(Mockito.any())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> pizzaTipoController.cadastrar(criaPizzaTipoDTO(criaPizzaTipo())));
    }

    @Test
    void teste8CadastrarFailDuplicated(){
        PizzaTipoDTO pizzaTipoDTO = new PizzaTipoDTO();
        pizzaTipoDTO.setNome("Calabresa");
        pizzaTipoDTO.setTamanho("Grande");
        pizzaTipoDTO.setValor(50.00);
        pizzaTipoDTO.setId(1L);
        Mockito.when(pizzaTipoRepository.existsByNome(Mockito.anyString())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> pizzaTipoController.cadastrar(pizzaTipoDTO));
        Assertions.assertFalse(exception.getMessage().contains("Tipo já cadastrado"));
    }
    @Test
    void teste9editarSuccess() {
        PizzaTipoDTO pizzaTipoDTO = criaPizzaTipoDTO(criaPizzaTipo());
        var pizzatipo = pizzaTipoController.editar(1L, pizzaTipoDTO);
        Assert.assertEquals("Tipo de pizza editado com sucesso", pizzatipo.getBody());
    }

   @Test
    void teste10AtualizarFailIdDiferentes() {
        Mockito.when(pizzaTipoRepository.existsByNome(Mockito.anyString())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> pizzaTipoController.editar(5L, criaPizzaTipoDTO(criaPizzaTipo())));
        Assertions.assertFalse(exception.getMessage().contains("coincidem"));
    }
    @Test
    void teste10AtualizarFailDuplicated(){
        PizzaTipo pizzaTipoDTO = new PizzaTipo();
        pizzaTipoDTO.setNome("Calabresa");
        pizzaTipoDTO.setTamanho("Grande");
        pizzaTipoDTO.setValor(50.00);
        pizzaTipoDTO.setId(5L);
        Mockito.when(pizzaTipoRepository.findByNome(Mockito.anyString())).thenReturn(pizzaTipoDTO);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> pizzaTipoController.editar(1L, criaPizzaTipoDTO(pizzaTipoDTO)));
        Assertions.assertTrue(exception.getMessage().contains("Tipo já cadastrado"));
    }
    @Test
    void teste11EditarFailCatch(){
        Mockito.when(pizzaTipoRepository.save(Mockito.any())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> pizzaTipoController.editar(5L,criaPizzaTipoDTO(criaPizzaTipo())));
    }

    @Test
    void teste12DeletarSuccess() {
        Mockito.when(pizzaTipoRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Assert.assertTrue(pizzaTipoController.deletar(1L).getBody().contains("Tipo de pizza deletado com sucesso"));
    }

    @Test
    void teste13DeletarFailCatch(){
        assertThrows(ResponseStatusException.class, () -> pizzaTipoController.deletar(5L));
    }

    @Test
    void teste14DeletarFailIdNotFound(){
        Mockito.when(pizzaTipoRepository.existsById(Mockito.anyLong())).thenReturn(false);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> pizzaTipoController.deletar(5L));
        Assertions.assertTrue(exception.getMessage().contains("cadastrado"));
    }

    @Test
    void teste15DesativarSuccess(){
        Mockito.when(pizzaTipoRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(pizzaTipoRepository.pizzaTipoExistTb_pizza(Mockito.anyLong())).thenReturn(true);
        Mockito.when(pizzaTipoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(criaPizzaTipo()));
        Assertions.assertTrue(pizzaTipoController.deletar(1L).getBody().contains("Tipo de pizza desativado com sucesso"));
    }

}
