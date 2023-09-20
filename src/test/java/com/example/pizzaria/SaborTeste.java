package com.example.pizzaria;


import com.example.pizzaria.controller.SaborController;
import com.example.pizzaria.dto.SaborDTO;
import com.example.pizzaria.entity.Sabor;
import com.example.pizzaria.repository.SaborRepository;
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
import java.util.Optional;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
 class SaborTeste {

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
        sabor.setSabor("Calabresa");
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
     void Teste1_FindByID() {
        var sabor = saborController.findById(1L);
        Assertions.assertEquals(1, sabor.getBody().getId(), 0);
    }

    @Test
     void teste2_FindAll() {
        var sabores = saborController.findAll();
        Assertions.assertEquals(1, sabores.getBody().size(), 0);
    }

    @Test
     void teste3_Cadastrar() {
        SaborDTO saborDTO = criaSaborDTO(criaSabor());
        var sabor = saborController.cadastrar(saborDTO);
        Assert.assertEquals("Sabor cadastrado com sucesso", sabor.getBody());
    }

    @Test
     void teste4_Atualizar() {
        SaborDTO saborDTO = criaSaborDTO(criaSabor());
        var sabor = saborController.editar(1l, saborDTO);
        Assert.assertEquals(200, sabor.getStatusCodeValue());
    }
}
