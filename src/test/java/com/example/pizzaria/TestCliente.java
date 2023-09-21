package com.example.pizzaria;

import com.example.pizzaria.controller.ClienteController;
import com.example.pizzaria.dto.ClienteDTO;
import com.example.pizzaria.entity.Cliente;
import com.example.pizzaria.entity.Sabor;
import com.example.pizzaria.repository.ClienteRepository;
import com.example.pizzaria.service.ClienteService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
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

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void Teste9DeleteService(){
        var cliente = clienteService.deletar(1l);
        Assert.assertEquals(true,cliente);

    }

    @Test
    void teste10findById_fail() {
        Mockito.when(clienteRepository.findById(5L)).thenReturn(Optional.empty());
        Assert.assertThrows(ResponseStatusException.class, () -> {
            clienteController.findById(5L);
        });


    }

    @Test
    void teste11findAll_fail() {
        Mockito.when(clienteRepository.findAll()).thenReturn(new ArrayList<Cliente>());
        Assert.assertThrows(ResponseStatusException.class, () -> {
            clienteController.findAll();
        });
    }

    @Test
    void teste12cadastrar_fail() {
        Mockito.when(clienteRepository.alreadyExists(Mockito.anyString())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> clienteController.cadastrar(criaClienteDto(criarCliente())));
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
        Long idInexistente = 100L;
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clienteController.deletar(idInexistente);
        });
        System.out.println(exception.getMessage());
        Assert.assertTrue(exception.getMessage().contains("Registro não encontrado"));
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
        Mockito.when(clienteRepository.alreadyExists(Mockito.anyString())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> clienteController.cadastrar(criaClienteDto(criarCliente())));
        Assert.assertTrue( exception.getMessage().contains("CPF"));
    }


}
