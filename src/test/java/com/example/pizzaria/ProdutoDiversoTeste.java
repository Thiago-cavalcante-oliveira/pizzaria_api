package com.example.pizzaria;


import com.example.pizzaria.controller.ProdutoDiversoController;
import com.example.pizzaria.dto.ProdutoDiversoDTO;
import com.example.pizzaria.entity.ProdutoDiverso;
import com.example.pizzaria.repository.ProdutoDiversoRepositorio;
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
public class ProdutoDiversoTeste {

    @MockBean
    ProdutoDiversoRepositorio repositorio;
    @Autowired
    ProdutoDiversoController controller;
    @BeforeEach
    void injectData() {
        ProdutoDiverso produtoDiverso = new ProdutoDiverso();
        produtoDiverso.setId(1l);
        produtoDiverso.setNome("coca-cola");
        produtoDiverso.setPreco(4.99);
        produtoDiverso.setTipo("refrigerante");
        produtoDiverso.setQuantidade(1);
        produtoDiverso.setAtivo(true);

        List<ProdutoDiverso> produtosDiversos = new ArrayList<>();
        produtosDiversos.add(produtoDiverso);

        Mockito.when(repositorio.findById(1l)).thenReturn(Optional.of(produtoDiverso));
        Mockito.when(repositorio.findAll()).thenReturn(produtosDiversos);
    }

    @Test
    void testeFindById() {
        var teste = controller.findById(1l);
        Assert.assertEquals(1l,teste.getBody().getId(),0);
    }

    @Test
    void testeFindAll()
    {
        var teste = controller.findAll();
        Assert.assertEquals(1, teste.getBody().size(),0);
    }

    @Test
    void testeCadastrar()
    {
        ProdutoDiversoDTO produtoDiversoDTO = new ProdutoDiversoDTO("coca-cola","refrigerante",1,4.99);
        var teste = controller.cadastrar(produtoDiversoDTO);
        Assert.assertTrue(teste.getBody().contains("Cadastrado"));
    }

    @Test
    void testeEditar()
    {
        ProdutoDiversoDTO produtoDiversoDTO = new ProdutoDiversoDTO("coca-cola","refrigerante",1,4.99);
        produtoDiversoDTO.setId(1l);
        var teste = controller.editar(1l, produtoDiversoDTO);
        Assert.assertTrue(teste.getBody().contains("Editado"));
    }

    @Test
    void testeDeletar()
    {
        var teste = controller.deletar(1l);
        Assert.assertTrue(teste.getBody().contains("Deletado"));
    }
}
