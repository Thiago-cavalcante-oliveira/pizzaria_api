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
        produtoDiverso.setTipo("Refrigerente 2l");
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
        assertThrows(ResponseStatusException.class, () -> controller.deletar(1L));
    }

    @Test
    void testeFindByIdFail() {

        Mockito.when(repositorio.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> controller.findById(1L));

    }

    @Test
    void testeFindAllFailMessage() {
        Mockito.when(repositorio.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ResponseStatusException.class, () -> controller.findAll());

    }

    @Test
    void testeCadastrarFail() {
        ProdutoDiversoDTO produtoDiversoDTO = criaProdutoDiversoDTO(criaProdutoDiverso());
        Mockito.when(repositorio.alreadyExists(Mockito.anyString())).thenReturn(true);
        assertThrows(ResponseStatusException.class, () -> controller.cadastrar(produtoDiversoDTO));
    }

    @Test
    void testeAtualizarFailIdDiferentes() {
        ProdutoDiversoDTO produtoDiversoDTO = criaProdutoDiversoDTO(criaProdutoDiverso());
        Mockito.when(repositorio.alreadyExists(Mockito.anyString())).thenReturn(true);
        assertThrows(ResponseStatusException.class, () ->  controller.editar(2l, produtoDiversoDTO));
    }

    @Test
    void teste2AtualizarSucess() {
        ProdutoDiversoDTO produtoDiversoDTO = new ProdutoDiversoDTO();
        produtoDiversoDTO.setTipo("Refrigerante 2l");
        produtoDiversoDTO.setPreco(10);
        produtoDiversoDTO.setQuantidade(3);
        produtoDiversoDTO.setNome("coca-cola");
        produtoDiversoDTO.setAtivo(true);
        produtoDiversoDTO.setId(1L);
        Mockito.when(repositorio.isTheSame(Mockito.anyString())).thenReturn(1l);
        Mockito.when(repositorio.alreadyExists(Mockito.anyString())).thenReturn(true);
        var teste = controller.editar(1l, produtoDiversoDTO);
        Assert.assertTrue(teste.getBody().contains("sucesso"));

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
        Mockito.when(repositorio.alreadyExists(Mockito.anyString())).thenReturn(true);
        System.out.println(assertThrows(ResponseStatusException.class, () -> controller.editar(5l, produtoDiversoDTO)));

        assertThrows(ResponseStatusException.class, () -> controller.editar(5l, produtoDiversoDTO));
    }

    @Test
    void testeHashEntity()
    {
        ProdutoDiverso produtoDiverso1 = new ProdutoDiverso("coca-cola", "Refrigerente 2l", 7.00, 2);
        ProdutoDiverso produtoDiverso2 = new ProdutoDiverso();
        produtoDiverso2.setNome("guarana");
        produtoDiverso2.setTipo("Refrigerente 600ml");
        produtoDiverso2.setPreco(3.00);
        produtoDiverso2.setQuantidade(5);

        Assertions.assertNotEquals(produtoDiverso1.getNome(), produtoDiverso2.getNome());
        Assertions.assertNotEquals(produtoDiverso1.getTipo(), produtoDiverso2.getTipo());
        Assertions.assertNotEquals(produtoDiverso1.getPreco(), produtoDiverso2.getPreco());
        Assertions.assertNotEquals(produtoDiverso1.getQuantidade(), produtoDiverso2.getQuantidade());

    }

    @Test
    void testeEqualsEntity()
    {
        ProdutoDiverso produtoDiverso1 = new ProdutoDiverso("coca-cola", "Refrigerente 2l", 7.00, 2);
        ProdutoDiverso produtoDiverso2 = new ProdutoDiverso();
        produtoDiverso2.setNome("coca-cola");
        produtoDiverso2.setTipo("Refrigerente 2l");
        produtoDiverso2.setPreco(7.00);
        produtoDiverso2.setQuantidade(2);

        Assertions.assertEquals(produtoDiverso1.getNome(), produtoDiverso2.getNome());
        Assertions.assertEquals(produtoDiverso1.getTipo(), produtoDiverso2.getTipo());
        Assertions.assertEquals(produtoDiverso1.getPreco(), produtoDiverso2.getPreco());
        Assertions.assertEquals(produtoDiverso1.getQuantidade(), produtoDiverso2.getQuantidade());
    }

    @Test
    void testeHashDTO()
    {
        ProdutoDiversoDTO produtoDiverso1 = new ProdutoDiversoDTO( "coca-cola", "Refrigerente 2l",  2, 7.00);
        produtoDiverso1.setId(1l);
        produtoDiverso1.setAtivo(true);
        ProdutoDiversoDTO produtoDiverso2 = new ProdutoDiversoDTO();
        produtoDiverso2.setId(2l);
        produtoDiverso2.setAtivo(false);
        produtoDiverso2.setNome("guarana");
        produtoDiverso2.setTipo("Refrigerente 600ml");
        produtoDiverso2.setPreco(3.00);
        produtoDiverso2.setQuantidade(5);

        Assertions.assertNotEquals(produtoDiverso1.getId(), produtoDiverso2.getId());
        Assertions.assertNotEquals(produtoDiverso1.isAtivo(), produtoDiverso2.isAtivo());
        Assertions.assertNotEquals(produtoDiverso1.getNome(), produtoDiverso2.getNome());
        Assertions.assertNotEquals(produtoDiverso1.getTipo(), produtoDiverso2.getTipo());
        Assertions.assertNotEquals(produtoDiverso1.getPreco(), produtoDiverso2.getPreco());
        Assertions.assertNotEquals(produtoDiverso1.getQuantidade(), produtoDiverso2.getQuantidade());

    }

    @Test
    void testeEqualsDTO()
    {
        ProdutoDiversoDTO produtoDiverso1 = new ProdutoDiversoDTO("coca-cola", "Refrigerente 2l",  2, 7.00);
        produtoDiverso1.setId(1l);
        produtoDiverso1.setAtivo(true);
        ProdutoDiversoDTO produtoDiverso2 = new ProdutoDiversoDTO();
        produtoDiverso2.setId(1l);
        produtoDiverso2.setAtivo(true);
        produtoDiverso2.setNome("coca-cola");
        produtoDiverso2.setTipo("Refrigerente 2l");
        produtoDiverso2.setPreco(7.00);
        produtoDiverso2.setQuantidade(2);


        Assertions.assertEquals(produtoDiverso1.getId(), produtoDiverso2.getId());
        Assertions.assertEquals(produtoDiverso1.isAtivo(), produtoDiverso2.isAtivo());
        Assertions.assertEquals(produtoDiverso1.getNome(), produtoDiverso2.getNome());
        Assertions.assertEquals(produtoDiverso1.getTipo(), produtoDiverso2.getTipo());
        Assertions.assertEquals(produtoDiverso1.getPreco(), produtoDiverso2.getPreco());
        Assertions.assertEquals(produtoDiverso1.getQuantidade(), produtoDiverso2.getQuantidade());
    }


    @Test
    void testeGetterSetterEntity()
    {
        ProdutoDiverso produtoDiverso = new ProdutoDiverso();

        produtoDiverso.setId(1l);
        produtoDiverso.setAtivo(true);
        produtoDiverso.setNome("coca-cola");
        produtoDiverso.setTipo("Refrigerente 2l");
        produtoDiverso.setPreco(7.00);
        produtoDiverso.setQuantidade(2);

        Assertions.assertEquals(1l, produtoDiverso.getId());
        Assertions.assertEquals(true, produtoDiverso.isAtivo());
        Assertions.assertEquals("coca-cola", produtoDiverso.getNome());
        Assertions.assertEquals("Refrigerente 2l", produtoDiverso.getTipo());
        Assertions.assertEquals(7.00, produtoDiverso.getPreco());
        Assertions.assertEquals(2, produtoDiverso.getQuantidade());
    }

    @Test
    void testeGetterSetterDTO()
    {
        ProdutoDiversoDTO produtoDiverso = new ProdutoDiversoDTO();
        produtoDiverso.setId(1l);
        produtoDiverso.setAtivo(true);
        produtoDiverso.setNome("coca-cola");
        produtoDiverso.setTipo("Refrigerente 2l");
        produtoDiverso.setPreco(7.00);
        produtoDiverso.setQuantidade(2);

        Assertions.assertEquals(1l, produtoDiverso.getId());
        Assertions.assertEquals(true, produtoDiverso.isAtivo());
        Assertions.assertEquals("coca-cola", produtoDiverso.getNome());
        Assertions.assertEquals("Refrigerente 2l", produtoDiverso.getTipo());
        Assertions.assertEquals(7.00, produtoDiverso.getPreco());
        Assertions.assertEquals(2, produtoDiverso.getQuantidade());
    }


}
