package com.example.pizzaria;

import com.example.pizzaria.controller.PedidoController;
import com.example.pizzaria.dto.*;
import com.example.pizzaria.entity.*;
import com.example.pizzaria.repository.PedidoRepository;
import com.example.pizzaria.service.PedidoService;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
 class TestPedido {

    @MockBean
    PedidoRepository repositorio;
    @Autowired
    PedidoController controller;

    @Autowired
    PedidoService service;

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
    void testeFindByIdService() {
        var teste = service.findById(1l);
        Assert.assertEquals(1l,teste.getId(),0);
    }

    @Test
    void testeFindAll()
    {
        var teste = controller.findAll();
        Assert.assertEquals(1, teste.getBody().size(),0);
    }

    @Test
    void testeFindAllService()
    {
        var teste = service.findAll();
        Assert.assertEquals(1, teste.size(),0);
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
    void testeAtualizarFailIdDiferentes() {
        PedidoDTO pedidoDTO = criaPedidoDTO(criaPedido());
        assertThrows(ResponseStatusException.class, () ->  controller.editar(2l, pedidoDTO));

    }

    @Test
    void testeCadastrarFail() {
        PedidoDTO pedidoDTO = null;
        assertThrows(ResponseStatusException.class, () -> controller.cadastrar(pedidoDTO));
    }

    @Test
    void testGetId() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        assertEquals(1L, pedido.getId());
    }

    @Test
    void testSetId() {
        Pedido pedido = new Pedido();
        pedido.setId(2L);
        assertEquals(2L, pedido.getId());
    }

    @Test
    void testGetCliente() {
        Pedido pedido = new Pedido();
        Cliente cliente = new Cliente();
        pedido.setCliente(cliente);
        assertEquals(cliente, pedido.getCliente());
    }

    @Test
    void testSetCliente() {
        Pedido pedido = new Pedido();
        Cliente cliente = new Cliente();
        pedido.setCliente(cliente);
        assertEquals(cliente, pedido.getCliente());
    }

    @Test
    void testGetSituacaoPedido() {
        Pedido pedido = new Pedido();
        pedido.setSituacaoPedido("Em andamento");
        assertEquals("Em andamento", pedido.getSituacaoPedido());
    }

    @Test
    void testSetSituacaoPedido() {
        Pedido pedido = new Pedido();
        pedido.setSituacaoPedido("Concluído");
        assertEquals("Concluído", pedido.getSituacaoPedido());
    }

    /*@Test
    void TesteGetterSetter(){
        Pedido pedido = new Pedido();
        pedido.setAtendente(TestFucionario.criaFuncionario());
        pedido.setEntregador(TestFucionario.criaFuncionario());
        pedido.setDataPedido(TestPedido.criaPedido().getDataPedido());
        pedido.setSituacaoPedido(TestPedido.criaPedido().getSituacaoPedido());
        pedido.setEndereco(TestPedido.criaPedido().getEndereco());
        pedido.setPizzas(TestPedido.criaPedido().getPizzas());
        pedido.setCliente(TestPedido.criaPedido().getCliente());
        pedido.setFormaPagamento(TestPedido.criaPedido().getFormaPagamento());
        pedido.setEntrega(TestPedido.criaPedido().isEntrega());
        pedido.setProdutos(TestPedido.criaPedido().getProdutos());
        pedido.setValorTotal(criaPedido().getValorTotal());
        pedido.setId(TestPedido.criaPedido().getId());

        Assertions.assertEquals(TestPedido.criaPedido().getAtendente(), pedido.getAtendente());
        Assertions.assertEquals(TestPedido.criaPedido().getDataPedido().getDay(), pedido.getDataPedido().getDay());
        Assertions.assertEquals(TestPedido.criaPedido().getSituacaoPedido(), pedido.getSituacaoPedido());
        Assertions.assertEquals(TestPedido.criaPedido().getPizzas(), pedido.getPizzas());
        Assertions.assertEquals(TestPedido.criaPedido().getEndereco(), pedido.getEndereco());
        Assertions.assertEquals(TestPedido.criaPedido().getProdutos(), pedido.getProdutos());
        Assertions.assertEquals(TestPedido.criaPedido().getValorTotal(), pedido.getValorTotal());
        Assertions.assertEquals(TestPedido.criaPedido().getFormaPagamento(), pedido.getFormaPagamento());
        Assertions.assertEquals(TestPedido.criaPedido().getCliente(), pedido.getCliente());
        Assertions.assertEquals(TestPedido.criaPedido().getPizzas(), pedido.getPizzas());
        Assertions.assertEquals(TestPedido.criaPedido().getEndereco(), pedido.getEndereco());
        Assertions.assertEquals(TestPedido.criaPedido().getEntregador(), pedido.getEntregador());
    }*/

    /*@Test
    void testeHAsh(){

        Pedido pedido = new Pedido();
        pedido.setAtendente(TestFucionario.criaFuncionario());
        pedido.setEntregador(TestFucionario.criaFuncionario());
        pedido.setDataPedido(TestPedido.criaPedido().getDataPedido());
        pedido.setSituacaoPedido(TestPedido.criaPedido().getSituacaoPedido());
        pedido.setEndereco(TestPedido.criaPedido().getEndereco());
        pedido.setPizzas(TestPedido.criaPedido().getPizzas());
        pedido.setCliente(TestPedido.criaPedido().getCliente());
        pedido.setFormaPagamento(TestPedido.criaPedido().getFormaPagamento());
        pedido.setEntrega(TestPedido.criaPedido().isEntrega());
        pedido.setProdutos(TestPedido.criaPedido().getProdutos());
        pedido.setValorTotal(criaPedido().getValorTotal());
        pedido.setId(TestPedido.criaPedido().getId());

        Pedido pedido1 = new Pedido();
        pedido1.setId(1L);
        Set<Pizza> pizzas = new HashSet<>();
        pizzas.add(TestPizza.criaPizza());
        pedido1.setPizzas(pizzas);
        pedido1.setEntrega(false);
        Set<ProdutoDiverso>produto = new HashSet<>();
        produto.add(TestProdutoDiverso.criaProdutoDiverso());
        pedido1.setProdutos(produto);
        pedido1.setEndereco(TestEndereco.criaEndereco());
        pedido1.setCliente(TestCliente.criarCliente());


        Assertions.assertEquals(pedido.getCliente(), pedido1.getCliente());
        Assertions.assertEquals(pedido.getPizzas(), pedido1.getPizzas());
        Assertions.assertEquals(pedido.getProdutos(), pedido1.getProdutos());
        Assertions.assertEquals(pedido.getEndereco(), pedido1.getEndereco());
        Assertions.assertEquals(pedido.getId(), pedido1.getId());

    }

    @Test
    void testeEquals(){

        Pedido pedido = new Pedido();
        pedido.setAtendente(TestFucionario.criaFuncionario());
        pedido.setEntregador(TestFucionario.criaFuncionario());
        pedido.setDataPedido(TestPedido.criaPedido().getDataPedido());
        pedido.setSituacaoPedido(TestPedido.criaPedido().getSituacaoPedido());
        pedido.setEndereco(TestPedido.criaPedido().getEndereco());
        pedido.setPizzas(TestPedido.criaPedido().getPizzas());
        pedido.setCliente(TestPedido.criaPedido().getCliente());
        pedido.setFormaPagamento(TestPedido.criaPedido().getFormaPagamento());
        pedido.setEntrega(TestPedido.criaPedido().isEntrega());
        pedido.setProdutos(TestPedido.criaPedido().getProdutos());
        pedido.setValorTotal(criaPedido().getValorTotal());
        pedido.setId(TestPedido.criaPedido().getId());

        Pedido pedido1 = new Pedido();
        pedido1.setId(1L);
        Set<Pizza> pizzas = new HashSet<>();
        pizzas.add(TestPizza.criaPizza());
        pedido1.setPizzas(pizzas);
        pedido1.setEntrega(false);
        Set<ProdutoDiverso>produto = new HashSet<>();
        produto.add(TestProdutoDiverso.criaProdutoDiverso());
        pedido1.setProdutos(produto);
        pedido1.setEndereco(TestEndereco.criaEndereco());
        pedido1.setCliente(TestCliente.criarCliente());


        Assertions.assertNotEquals(pedido1,pedido);
    }*/
}
