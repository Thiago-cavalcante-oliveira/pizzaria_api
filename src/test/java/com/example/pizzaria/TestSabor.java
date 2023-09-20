package com.example.pizzaria;


import com.example.pizzaria.controller.SaborController;
import com.example.pizzaria.dto.SaborDTO;
import com.example.pizzaria.entity.Sabor;
import com.example.pizzaria.repository.SaborRepository;
import com.example.pizzaria.service.SaborService;
import org.junit.Assert;
import org.junit.FixMethodOrder;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestSabor {

    @MockBean
    SaborRepository saborRepository;
    @Autowired
    SaborService saborService;
    @Autowired
    SaborController saborController;
    static
    ModelMapper modelMapper = new ModelMapper();

    protected static Sabor criaSabor() {
        Sabor sabor = new Sabor();
        sabor.setId(1L);
        sabor.setNomeSabor("Calabresa");
        sabor.setValor(10.00);
        sabor.setIngredientes("Calabresa, queijo, molho de tomate");
        return sabor;
    }

    protected static List<Sabor> listaSabores() {
        List<Sabor> sabores = new ArrayList<>();
        sabores.add(criaSabor());
        return sabores;
    }

    protected static SaborDTO criaSaborDTO(Sabor sabor) {
        return modelMapper.map(sabor, SaborDTO.class);
    }

    @BeforeEach
    void injectDados() {
        Mockito.when(saborRepository.findById(criaSabor().getId())).thenReturn(Optional.of(criaSabor()));
        Mockito.when(saborRepository.findByNome(criaSaborDTO(criaSabor()).getNome())).thenReturn(criaSabor());
        Mockito.when(saborRepository.findAll()).thenReturn(listaSabores());
        Mockito.when(saborRepository.save(criaSabor())).thenReturn(criaSabor());
    }

    @Test
    void Teste1FindByIDSuccess() {
        var sabor = saborController.findById(1L);
        Assertions.assertEquals(1, sabor.getBody().getId(), 0);
    }

    @Test
    void teste2FindByIdFail() {
        Mockito.when(saborRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> saborController.findById(1L));
    }

    @Test
    void teste3FindAllSuccess() {
        var sabores = saborController.findAll();
        Assertions.assertEquals(1, sabores.getBody().size(), 0);
    }

    @Test
    void teste4FindAllFailMessage() {
        Mockito.when(saborRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> saborController.findAll());
        Assertions.assertTrue(exception.getMessage().contains("Sabor não cadastrado"));
    }

    @Test
    void teste5FindAllFailException() {
        Mockito.when(saborRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> saborController.findAll());
        Assertions.assertThrows(ResponseStatusException.class, () -> saborController.findAll());
    }

    @Test
    void teste6CadastrarSuccess() {
        SaborDTO saborDTO = criaSaborDTO(criaSabor());
        var sabor = saborController.cadastrar(saborDTO);
        Assert.assertEquals("Sabor cadastrado com sucesso", sabor.getBody());
    }

    @Test
    void teste7CadastrarFail() {
        Mockito.when(saborRepository.existsByNome(Mockito.anyString())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> saborController.cadastrar(criaSaborDTO(criaSabor())));
        Assertions.assertTrue(exception.getMessage().contains("Sabor já cadastrado"));
    }

    @Test
    void teste8AtualizarSuccess() {
        SaborDTO saborDTO = criaSaborDTO(criaSabor());
        var sabor = saborController.editar(1l, saborDTO);
        Assert.assertEquals(200, sabor.getStatusCodeValue());
    }

    @Test
    void teste9AtualizarFailIdDiferentes() {
        Mockito.when(saborRepository.existsByNome(Mockito.anyString())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> saborController.editar(5L, criaSaborDTO(criaSabor())));
        Assertions.assertTrue(exception.getMessage().contains("coincidem"));
    }

    @Test
    void teste10AtualizarFailDuplicated() {
        SaborDTO saborDTO = new SaborDTO();
        saborDTO.setIngredientes("Calabresa, queijo, molho de tomate");
        saborDTO.setNome("Calabresa");
        saborDTO.setId(5L);
        Mockito.when(saborRepository.findByNome(Mockito.anyString())).thenReturn(criaSabor());
        ResponseStatusException exceptio = assertThrows(ResponseStatusException.class,
                () -> saborController.editar(1L, saborDTO));
        Assertions.assertFalse(exceptio.getMessage().contains("Sabor já cadastrado"));
    }


    @Test
    void teste12DesativarSuccess() {
        Mockito.when(saborRepository.saborExistTb_pizza(Mockito.anyLong())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> saborController.deletar(1L));
        Assertions.assertTrue(exception.getMessage().contains("Sabor desativado"));
    }



}



