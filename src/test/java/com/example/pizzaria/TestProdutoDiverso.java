package com.example.pizzaria;


import com.example.pizzaria.controller.ProdutoDiversoController;
import com.example.pizzaria.dto.ProdutoDiversoDTO;
import com.example.pizzaria.entity.ProdutoDiverso;
import com.example.pizzaria.repository.ProdutoDiversoRepositorio;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        Mockito.when(repositorio.findAll()).thenReturn(listaProdutosDiverso());
        Mockito.when(repositorio.isTheSame(criaProdutoDiverso().getTipo())).thenReturn(1L);
        Mockito.when(repositorio.alreadyExists(criaProdutoDiverso().getTipo())).thenReturn(false);
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
        var teste = controller.cadastrar(criaProdutoDiversoDTO(criaProdutoDiverso()));
        Assert.assertTrue(teste.getBody().contains("sucesso"));
    }

    @Test
    void testeEditar()
    {
        var teste = controller.editar( 1l, criaProdutoDiversoDTO(criaProdutoDiverso()) );
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
        Mockito.when(repositorio.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controller.deletar(1L));
    }

    @Test
    void testeFindByIdFail() {

        Mockito.when(repositorio.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> controller.findById(1L));

    }

    @Test
    void testeCadastrarFail() {
        Mockito.when(repositorio.alreadyExists(Mockito.anyString())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> controller.cadastrar(criaProdutoDiversoDTO(criaProdutoDiverso())));
        Assertions.assertTrue(exception.getMessage().contains("Tipo já cadastrado"));
    }

    @Test
    void testeAtualizarFailIdDiferentes() {
        Mockito.when(repositorio.alreadyExists(Mockito.anyString())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () ->  controller.editar(2l, criaProdutoDiversoDTO(criaProdutoDiverso())));
        Assertions.assertFalse(exception.getMessage().contains("Tipo já cadastrado"));
    }

    @Test
    void testeAtualizarFailDuplicated() {
        ProdutoDiversoDTO produtoDiversoDTO = new ProdutoDiversoDTO();
        produtoDiversoDTO.setTipo("Refrigerante 2l");
        produtoDiversoDTO.setPreco(10);
        produtoDiversoDTO.setQuantidade(3);
        produtoDiversoDTO.setNome("coca-cola");
        produtoDiversoDTO.setAtivo(true);
        produtoDiversoDTO.setId(5L);
        Mockito.when(repositorio.isTheSame(Mockito.anyString())).thenReturn(1l);
        ResponseStatusException exceptio = assertThrows(ResponseStatusException.class,
                () -> controller.editar(5l, produtoDiversoDTO));
        Assertions.assertFalse(exceptio.getMessage().contains("Tipo já cadastrado"));
    }

}
