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
public class PedidoTeste {

    @MockBean
    PedidoRepository repositorio;
    @Autowired
    PedidoController controller;

    static ModelMapper modelMapper = new ModelMapper();
    static ClienteTeste clienteTeste;
    static FucionarioTeste fucionarioTeste;
    static PizzaTeste pizzaTeste;
    static ProdutoDiversoTeste produtoDiversoTeste;
    static EnderecoTeste enderecoTeste;

    protected static Pedido criaPedido()
    {
        Set<Pizza> pizzas = new HashSet<>();
        pizzas.add(pizzaTeste.criaPizza());
        Set<ProdutoDiverso> produtos = new HashSet<>();
        produtos.add(produtoDiversoTeste.criaProdutoDiverso());

        Date data = new Date();
        Pedido pedido = new Pedido();
        pedido.setId(1l);
        pedido.setAtivo(true);
        pedido.setCliente(clienteTeste.criarCliente());
        pedido.setAtendente(fucionarioTeste.criaFuncionario());
        pedido.setEndereco(enderecoTeste.criaEndereco());
        pedido.setEntregador(fucionarioTeste.criaFuncionario());
        pedido.setSolicitaEntrega(true);
        pedido.setPedido("pedido");
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
        Assert.assertTrue(teste.getBody().contains("cadastrado"));
    }

    @Test
    void testeEditar()
    {
        var teste = controller.editar(1l, criaPedidoDTO(criaPedido()));
        Assert.assertTrue(teste.getBody().contains("alterado"));
    }

    @Test
    void testeDeletar()
    {
        var teste = controller.deletar(1l);
        Assert.assertTrue(teste.getBody().contains("desativado"));
    }

}
