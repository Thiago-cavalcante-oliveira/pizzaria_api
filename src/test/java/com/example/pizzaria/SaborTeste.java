package com.example.pizzaria;

import com.example.pizzaria.config.ModelMapperConfig;
import com.example.pizzaria.controller.SaborController;
import com.example.pizzaria.dto.SaborDTO;
import com.example.pizzaria.entity.Sabor;
import com.example.pizzaria.repository.SaborRepository;
import com.example.pizzaria.service.SaborService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class SaborTeste {

    @MockBean
    SaborRepository saborRepository;
    @Autowired
    SaborService saborService;
    @Autowired
    SaborController saborController;
    @Autowired
    ModelMapperConfig modelMapperConfig;

    protected Sabor criaSabor() {
        Sabor sabor = new Sabor();
        sabor.setId(1l);
        sabor.setSabor("Calabresa");
        sabor.setValor(10.00);
        sabor.setIngredientes("Calabresa, queijo, molho de tomate");
        return sabor;
    }

    protected SaborDTO criaSaborDTO(Sabor sabor) {
        return modelMapperConfig.modelMapper().map(sabor, SaborDTO.class);
    }

    @BeforeEach
    void injectDados() {
        Sabor sabor1 = new Sabor();
        sabor1 = criaSabor();
        SaborDTO saborDTO1 = new SaborDTO();
        saborDTO1 = criaSaborDTO(sabor1);

        List<Sabor> sabores = new ArrayList<>();
        sabores.add(sabor1);

        Mockito.when(saborRepository.findById(sabor1.getId())).thenReturn(Optional.of(sabor1));
        Mockito.when(saborRepository.findByNome(sabor1.getSabor())).thenReturn(sabor1);
        Mockito.when(saborRepository.findAll()).thenReturn(sabores);
        Mockito.when(saborRepository.save(sabor1)).thenReturn(sabor1);
    }

    @Test
    void TesteFindByID() {
        var sabor = saborController.findById(1l);
        Assert.assertEquals(1, sabor.getBody().getId(), 0);
    }

    @Test
    void testeFindAll() {
        var sabores = saborController.findAll();
        Assert.assertEquals(1, sabores.getBody().size(), 0);
    }

    @Test
    void testeCadastrar() {
        SaborDTO saborDTO = new SaborDTO();
        saborDTO = criaSaborDTO(criaSabor());
        var sabor = saborController.cadastrar(saborDTO);
        Assert.assertEquals("Sabor cadastrado com sucesso", sabor.getBody());
    }

    @Test
    void testeAtualizar() {
        SaborDTO saborDTO = new SaborDTO();
        saborDTO = criaSaborDTO(criaSabor());

        var sabor = saborController.editar(1l, saborDTO);
        Assert.assertEquals(200, sabor.getStatusCodeValue());
    }
}
