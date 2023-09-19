package com.example.pizzaria;

import com.example.pizzaria.controller.PedidoController;
import com.example.pizzaria.dto.*;
import com.example.pizzaria.entity.*;
import com.example.pizzaria.repository.PedidoRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

    SaborTeste saborTeste;
    @BeforeEach
    void injectData() {
        ProdutoDiverso produtoDiverso = new ProdutoDiverso();
        produtoDiverso.setId(1l);
        produtoDiverso.setNome("coca-cola");
        produtoDiverso.setPreco(4.99);
        produtoDiverso.setTipo("refrigerante");
        produtoDiverso.setQuantidade(1);
        produtoDiverso.setAtivo(true);

        Cliente cliente = new Cliente("Lucas","99 99999-9999", "999.999.999-99");
        cliente.setId(1l);
        cliente.setAtivo(true);
        Endereco endereco = new Endereco("8888-8888","Carmin",777,"Neves","88888-888","casa",cliente);
        endereco.setId(1l);
        endereco.setAtivo(true);
        Funcionario atendente = new Funcionario("Cauan", "777.777.777-77", "Atendente");
        atendente.setId(1l);
        atendente.setAtivo(true);
        Funcionario entregador = new Funcionario("Vitor", "888.888.888-88", "Entregador");
        entregador.setId(1l);
        entregador.setAtivo(true);
        Sabor sabor = new Sabor("Milho","Milho, Queijo, Massa de tomate", 1.99);
        sabor.setId(1l);
        sabor.setAtivo(true);
        PizzaTipo pizzaTipo = new PizzaTipo("pizza", "pequeno", 35);
        pizzaTipo.setId(1l);
        pizzaTipo.setAtivo(true);
        Pizza pizza = new Pizza(pizzaTipo, SaborTeste.criaSabor());
        pizza.setId(1l);
        pizza.setAtivo(true);
        Set<Pizza> pizzas = new HashSet<>();
        pizzas.add(pizza);
        Set<ProdutoDiverso> produtos = new HashSet<>();
        produtos.add(produtoDiverso);
        Date data = new Date();

        Pedido pedido = new Pedido(cliente,atendente,endereco,entregador,true,"pedido",50,"dinheiro",false,data,pizzas,produtos);
        pedido.setId(1l);
        pedido.setAtivo(true);
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedido);


        Mockito.when(repositorio.findById(1l)).thenReturn(Optional.of(pedido));
        Mockito.when(repositorio.findAll()).thenReturn(pedidos);
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
        PedidoDTO pedidoDTO = criaPedido();
        var teste = controller.cadastrar(pedidoDTO);
        Assert.assertTrue(teste.getBody().contains("cadastrado"));
    }

    @Test
    void testeEditar()
    {
        PedidoDTO pedidoDTO = criaPedido();
        var teste = controller.editar(1l, pedidoDTO);
        Assert.assertTrue(teste.getBody().contains("alterado"));
    }

    @Test
    void testeDeletar()
    {
        var teste = controller.deletar(1l);
        Assert.assertTrue(teste.getBody().contains("desativado"));
    }

    private PedidoDTO criaPedido()
    {
        ProdutoDiversoDTO produtoDiverso = new ProdutoDiversoDTO("coca-cola", "refrigerante", 1, 4.99);
        ClienteDTO cliente = new ClienteDTO("Lucas","99 99999-9999", "999.999.999-99");
        EnderecoDTO endereco = new EnderecoDTO("8888-8888","Carmin",777,"Neves","88888-888","casa",cliente);
        FuncionarioDTO atendente = new FuncionarioDTO("Cauan", "777.777.777-77", "Atendente");
        FuncionarioDTO entregador = new FuncionarioDTO("Vitor", "888.888.888-88", "Entregador");
        SaborDTO sabor = new SaborDTO("Milho","Milho, Queijo, Massa de tomate", 1.99);
        PizzaTipoDTO pizzaTipo = new PizzaTipoDTO("pizza", "pequeno", 35);
        PizzaDTO pizza = new PizzaDTO(pizzaTipo, sabor);
        Set<PizzaDTO> pizzas = new HashSet<>();
        pizzas.add(pizza);
        Set<ProdutoDiversoDTO> produtos = new HashSet<>();
        produtos.add(produtoDiverso);
        Date data = new Date();

        PedidoDTO pedido = new PedidoDTO(cliente,atendente,endereco,entregador,true,"pedido",50,"dinheiro",false,data,pizzas,produtos);
        pedido.setId(1l);
        pedido.setAtivo(true);
        return pedido;

    }

}
