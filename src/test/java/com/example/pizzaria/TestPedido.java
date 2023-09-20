package com.example.pizzaria;

import com.example.pizzaria.controller.PedidoController;
import com.example.pizzaria.dto.*;
import com.example.pizzaria.entity.*;
import com.example.pizzaria.repository.PedidoRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

@SpringBootTest
 class TestPedido {

    @MockBean
    PedidoRepository repositorio;
    @Autowired
    PedidoController controller;

    static ModelMapper modelMapper = new ModelMapper();
    static TestCliente testCliente;
    static TestFucionario testFucionario;
    static TestPizza testPizza;
    static TestProdutoDiverso testProdutoDiverso;
    static TestEndereco testEndereco;

    protected static Pedido criaPedido()
    {
        Set<Pizza> pizzas = new HashSet<>();
        pizzas.add(TestPizza.criaPizza());
        Set<ProdutoDiverso> produtos = new HashSet<>();
        produtos.add(TestProdutoDiverso.criaProdutoDiverso());

        Date data = new Date();
        Pedido pedido = new Pedido();
        pedido.setId(1l);
        pedido.setAtivo(true);
        pedido.setCliente(TestCliente.criarCliente());
        pedido.setAtendente(TestFucionario.criaFuncionario());
        pedido.setEndereco(TestEndereco.criaEndereco());
        pedido.setEntregador(TestFucionario.criaFuncionario());
        pedido.setSolicitaEntrega(true);
        pedido.setSituacaoPedido("pedido");
        pedido.setValorTotal(50);
        pedido.setFormaPagamento("dinheiro");
        pedido.setEntrega(false);
        pedido.setDataPedido(data);
        pedido.setPizzas(pizzas);
        pedido.setProdutos(produtos);

        return pedido;
    }

    protected static List<Pedido> listaPedidos()
    {
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(criaPedido());
        return pedidos;
    }

    protected static PedidoDTO criaPedidoDTO(Pedido pedido)
    {
        return modelMapper.map(pedido, PedidoDTO.class);
    }




    @BeforeEach
    void injectData() {

        Mockito.when(repositorio.findById(1l)).thenReturn(Optional.of(criaPedido()));
        Mockito.when(repositorio.findAll()).thenReturn(listaPedidos());
        Mockito.when(repositorio.doesExist(1l)).thenReturn(true);
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
        var teste = controller.cadastrar(criaPedidoDTO(criaPedido()));
        Assert.assertTrue(teste.getBody().contains("sucesso"));
    }

    @Test
    void testeEditar()
    {
        var teste = controller.editar(1l, criaPedidoDTO(criaPedido()));
        Assert.assertTrue(teste.getBody().contains("sucesso"));
    }

    @Test
    void testeDeletar()
    {
        var teste = controller.deletar(1l);
        Assert.assertTrue(teste.getBody().contains("sucesso"));
    }

}
