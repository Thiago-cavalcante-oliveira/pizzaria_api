package com.example.pizzaria;

import com.example.pizzaria.controller.ClienteController;
import com.example.pizzaria.dto.ClienteDTO;
import com.example.pizzaria.entity.Cliente;
import com.example.pizzaria.repository.ClienteRepository;
import com.example.pizzaria.service.ClienteService;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
 class TestCliente {
    @MockBean
    ClienteRepository clienteRepository;
    @Autowired
    ClienteService clienteService;
    @Autowired
    ClienteController clienteController;

    static
    ModelMapper modelMapper = new ModelMapper();

    protected static Cliente criarCliente(){
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Eduardo");
        cliente.setCpf("109.999.789-98");
        cliente.setTelCelular("45 99985-5563");
        return cliente;
    }

    protected static List<Cliente> listaClientes(){
        List<Cliente> clientes  = new ArrayList<>();
        clientes.add(criarCliente());
        return clientes;
    }

    protected static ClienteDTO criaClienteDto( Cliente cliente){
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @BeforeEach
    void injectDado(){
        Mockito.when(clienteRepository.save(criarCliente())).thenReturn(criarCliente());
        Mockito.when(clienteRepository.findById(criarCliente().getId())).thenReturn(Optional.of(criarCliente()));
        Mockito.when(clienteRepository.findAll()).thenReturn(listaClientes());
        Mockito.when(clienteRepository.isTheSame(criarCliente().getCpf())).thenReturn(1L);
        Mockito.when(clienteRepository.doesExist(criarCliente().getId())).thenReturn(criarCliente().isAtivo());
        Mockito.when(clienteRepository.alreadyExists(criarCliente().getCpf())).thenReturn(false);
    }

    @Test
     void Teste1FindByIDController(){
        var cliente = clienteController.findById(1L);
        Assert.assertEquals(1L,cliente.getBody().getId(),0);
    }

    @Test
    void Teste2FindByIDService(){
        var cliente = clienteService.findById(1L);
        Assert.assertEquals(1L, cliente.getId(),0);
    }

   @Test
     void Teste3FindByAllController(){
        var clientes = clienteController.findAll();
        Assert.assertEquals(1, clientes.getBody().size());
    }

    @Test
    void Teste4FindByAllService(){
        var clientes = clienteService.findAll();
        Assert.assertEquals(1, clientes.size());
    }

    @Test
    void Teste5CadastrarClienteController(){
        ClienteDTO clienteDTO = criaClienteDto(criarCliente());
        var cliente = clienteController.cadastrar(clienteDTO);
        Assert.assertEquals("Operação realizada com sucesso",cliente.getBody());
    }



    @Test
     void Teste6AtualizarController(){
        ClienteDTO clienteDTO = criaClienteDto(criarCliente());
        var cliente = clienteController.editar(1L, clienteDTO);
        Assert.assertEquals(200, cliente.getStatusCodeValue());

    }

    @Test
    void Teste7AtualizarService(){
        ClienteDTO clienteDTO = criaClienteDto(criarCliente());
        var cliente = clienteController.editar(1L, clienteDTO);
        Assert.assertEquals(200, cliente.getStatusCodeValue());

    }

    @Test
    void Teste8DeleteController(){
        var cliente = clienteController.deletar(1l);
        Assert.assertEquals("Cliente desativado",cliente.getBody());

    }



    @Test
    void teste10findById_fail() {
        Mockito.when(clienteRepository.findById(5L)).thenReturn(Optional.empty());
        Assert.assertThrows(ResponseStatusException.class, () -> {
            clienteController.findById(5L);
        });


    }

    @Test
    void teste11findAllFail() {
        Mockito.when(clienteRepository.findAll()).thenReturn(new ArrayList<Cliente>());
        Assert.assertThrows(ResponseStatusException.class, () -> {
            clienteController.findAll();
        });
    }

    @Test
    void teste12cadastrarFail() {
        ClienteDTO clienteDTO = criaClienteDto(criarCliente());
        Mockito.when(clienteRepository.alreadyExists(Mockito.anyString())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> clienteController.cadastrar(clienteDTO));
        Assert.assertTrue( exception.getMessage().contains("CPF não encontrado"));
    }

    @Test
    void teste13AtualizacaoMalSucedida() {
        Long idInexistente = 100L;
        ClienteDTO clienteDTO = criaClienteDto(criarCliente());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clienteController.editar(idInexistente, clienteDTO);
        });
        System.out.println(exception.getMessage());
        Assert.assertTrue(exception.getMessage().contains("Registro não encontrado"));


    }


    @Test
    void teste14ExclusaoMalSucedida() {
        Mockito.when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clienteController.deletar(1L);
        });
        System.out.println(exception.getMessage());
        Assertions.assertTrue(exception.getMessage().contains("Registro não encontrado"));
    }

    @Test
    void teste15CadastroComCPFDuplicado() {
        ClienteDTO clienteDTO = criaClienteDto(criarCliente());
        Mockito.when(clienteRepository.alreadyExists(clienteDTO.getCpf())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clienteController.cadastrar(clienteDTO);
        });
        System.out.println(exception.getMessage());
        Assert.assertTrue(exception.getMessage().contains("CPF não encontrado"));

    }

    @Test
    void teste16ListagemVazia() {
        Mockito.when(clienteRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clienteController.findAll();
        });
        System.out.println(exception.getMessage());
        Assert.assertTrue(exception.getMessage().contains("Lista não encontrada"));
    }
    @Test
    void teste13CadastrarFail() {
        ClienteDTO clienteDTO = criaClienteDto(criarCliente());
        Mockito.when(clienteRepository.alreadyExists(Mockito.anyString())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> clienteController.cadastrar(clienteDTO));
        Assert.assertTrue( exception.getMessage().contains("CPF"));
    }


    @Test
    void teste14GetSetNome() {
        Cliente cliente = new Cliente();
        cliente.setNome("Eduardo");
        assertEquals("Eduardo", cliente.getNome());
    }

    @Test
    void teste15GetSetTelCelular() {
        Cliente cliente = new Cliente();
        cliente.setTelCelular("45 99985-5563");
        assertEquals("45 99985-5563", cliente.getTelCelular());
    }

    @Test
    void teste16GetSetCpf() {
        Cliente cliente = new Cliente();
        cliente.setCpf("109.999.789-98");
        assertEquals("109.999.789-98", cliente.getCpf());
    }

    @Test
    void teste17SetNomeComValorNulo() {
        ClienteDTO clienteDTO = new ClienteDTO();
        assertThrows(IllegalArgumentException.class, () -> clienteDTO.setNome(null));
    }

    @Test
    void teste18SetNomeComValorEmBranco() {
        ClienteDTO clienteDTO = new ClienteDTO();
        assertThrows(IllegalArgumentException.class, () -> clienteDTO.setNome(""));
    }

    @Test
    void teste20SetTelCelularComValorNulo() {
        ClienteDTO clienteDTO = new ClienteDTO();
        assertThrows(IllegalArgumentException.class, () -> clienteDTO.setTelCelular(null));
    }

    @Test
    void teste21SetTelCelularComValorEmBranco() {
        ClienteDTO clienteDTO = new ClienteDTO();
        assertThrows(IllegalArgumentException.class, () -> clienteDTO.setTelCelular(""));
    }
    @Test
    void teste22HashDto() {

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("Eduardo");
        clienteDTO.setTelCelular("45 99815-2683");
        clienteDTO.setCpf("109.999.888-78");
        clienteDTO.setId(1L);

        ClienteDTO clienteDTO2 = new ClienteDTO();
        clienteDTO2.setNome("Yasmin");
        clienteDTO2.setTelCelular("45 99635-2683");
        clienteDTO2.setCpf("109.909.868-78");
        clienteDTO2.setId(2L);


        assertNotEquals(clienteDTO, clienteDTO2);

        assertNotEquals(clienteDTO.hashCode(), clienteDTO2.hashCode());
    }

    @Test
    void teste23HashEntity() {

        Cliente cliente = new Cliente();
        cliente.setNome("Eduardo");
        cliente.setTelCelular("45 99815-2683");
        cliente.setCpf("109.999.888-78");
        cliente.setId(1L);

        Cliente cliente1 = new Cliente();
        cliente1.setNome("Yasmin");
        cliente1.setTelCelular("45 99635-2683");
        cliente1.setCpf("109.909.868-78");
        cliente1.setId(2L);


        assertNotEquals(cliente, cliente1);

        assertNotEquals(cliente1.hashCode(), cliente.hashCode());
    }

    @Test
    void teste24GetSetNomeDTO() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("Eduardo");
        assertEquals("Eduardo", clienteDTO.getNome());
    }

    @Test
    void teste25GetSetTelCelularDTO() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setTelCelular("45 99985-5563");
        assertEquals("45 99985-5563", clienteDTO.getTelCelular());
    }

    @Test
    void teste26GetSetCpfDTO() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCpf("109.999.789-98");
        assertEquals("109.999.789-98", clienteDTO.getCpf());
    }
}
