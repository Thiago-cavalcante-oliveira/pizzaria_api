package com.example.pizzaria;


import com.example.pizzaria.controller.ProdutoDiversoController;
import com.example.pizzaria.dto.ProdutoDiversoDTO;
import com.example.pizzaria.entity.ProdutoDiverso;
import com.example.pizzaria.repository.ProdutoDiversoRepositorio;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
 class TestProdutoDiverso {

    @MockBean
    ProdutoDiversoRepositorio repositorio;
    @Autowired
    ProdutoDiversoController controller;

    static ModelMapper modelMapper = new ModelMapper();

    protected static ProdutoDiverso criaProdutoDiverso(){
        ProdutoDiverso produtoDiverso = new ProdutoDiverso();
        produtoDiverso.setId(1l);
        produtoDiverso.setNome("coca-cola");
        produtoDiverso.setPreco(4.99);
        produtoDiverso.setTipo("refrigerante");
        produtoDiverso.setQuantidade(1);
        produtoDiverso.setAtivo(true);
        return produtoDiverso;
    }

    protected static List<ProdutoDiverso> listaProdutosDiverso()
    {
        List<ProdutoDiverso> produtosDiversos = new ArrayList<>();
        produtosDiversos.add(criaProdutoDiverso());

        return  produtosDiversos;
    }

    protected static ProdutoDiversoDTO criaProdutoDiversoDTO(ProdutoDiverso produtoDiverso){
        return modelMapper.map(produtoDiverso, ProdutoDiversoDTO.class);
    }


    @BeforeEach
    void injectData() {

        Mockito.when(repositorio.findById(1l)).thenReturn(Optional.of(criaProdutoDiverso()));
        Mockito.when(repositorio.findById(2l)).thenReturn(Optional.of(null));
        Mockito.when(repositorio.findAll()).thenReturn(listaProdutosDiverso());
    }

    @Test
    void testeFindById() {
        var teste = controller.findById(1l);
        Assert.assertEquals(1l,teste.getBody().getId(),0);
    }

    @Test
    void testeFindByIdFail() {

        var teste = controller.findById(2l);
        System.out.println(teste);
        Assert.assertTrue(teste.getStatusCode().isError()) ;
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
        var teste = controller.cadastrar(criaProdutoDiversoDTO(criaProdutoDiverso()));
        Assert.assertTrue(teste.getBody().contains("sucesso"));
    }

    @Test
    void testeEditar()
    {
        var teste = controller.editar( criaProdutoDiversoDTO(criaProdutoDiverso()));
        Assert.assertTrue(teste.getBody().contains("sucesso"));
    }

    @Test
    void testeDeletar()
    {
        var teste = controller.deletar(1l);
        Assert.assertTrue(teste.getBody().contains("sucesso"));
    }

    @Test
    void testeDeletarFail()
    {
        var teste = controller.deletar(2l);
        Assert.assertTrue(teste.getStatusCode().isError()) ;
    }

}
