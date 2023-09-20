package com.example.pizzaria;

import com.example.pizzaria.controller.EnderecoController;
import com.example.pizzaria.dto.EnderecoDTO;
import com.example.pizzaria.entity.Endereco;
import com.example.pizzaria.entity.Sabor;
import com.example.pizzaria.repository.EnderecoRepository;
import com.example.pizzaria.service.EnderecoService;
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
 class TestEndereco {
    @MockBean
    EnderecoRepository enderecoRepository;
    @Autowired
    EnderecoController enderecoController;
    @Autowired
    EnderecoService enderecoService;

    static ModelMapper modelMapper = new ModelMapper();

    static TestCliente testCliente;

    protected static Endereco criaEndereco()
    {
        Endereco endereco = new Endereco();
        endereco.setId(1l);
        endereco.setCep("85858-330");
        endereco.setNuEndereco(1445);
        endereco.setRua("Jose epinafio teles Costa");
        endereco.setBairro("Morumbi");
        endereco.setTelResidencia("45 99999-8855");
        endereco.setComplemento("casa");
        endereco.setCliente(TestCliente.criarCliente());
        return endereco;
    }

    protected static List<Endereco> listaEndereco()
    {
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(criaEndereco());
        return enderecos;
    }

    protected static EnderecoDTO criaEnderecoDTO(Endereco endereco)
    {
        return modelMapper.map(endereco, EnderecoDTO.class);
    }


    @BeforeEach
    void injectDados(){

        Mockito.when(enderecoRepository.findById(criaEndereco().getId())).thenReturn(Optional.of(criaEndereco()));
        Mockito.when(enderecoRepository.findAll()).thenReturn(listaEndereco());
        Mockito.when(enderecoRepository.doesExist(criaEndereco().getId())).thenReturn(true);
        Mockito.when(enderecoRepository.save(criaEndereco())).thenReturn(criaEndereco());
    }

    @Test
    void Teste1FindByIdController(){
        var endereco = enderecoController.findById(1l);
        Assert.assertEquals(1l, endereco.getBody().getId(), 0);
    }

    @Test
    void Teste2FindByIdService(){
        var endereco = enderecoService.findById(1l);
        Assert.assertEquals(1l, endereco.getId(), 0);
    }

    @Test
    void Teste3FindByAllController(){
        var endereco = enderecoController.findAll();
        Assert.assertEquals(1, endereco.getBody().size());
    }

    @Test
    void Teste4FindByAllService(){
        var endereco = enderecoService.findAll();
        Assert.assertEquals(1, endereco.size());
    }

    @Test
    void Teste5CadastrarEnderecoController(){
        var endereco = enderecoController.cadastrar(criaEnderecoDTO(criaEndereco()));
        Assert.assertEquals("Operacao realizada com sucesso", endereco.getBody());
    }

    @Test
    void Teste6AtualizarController(){

        var endereco = enderecoController.editar(1l, criaEnderecoDTO(criaEndereco()));
        Assert.assertEquals(200, endereco.getStatusCodeValue());
    }


    @Test
    void Teste7DeletarController(){
        var endereco = enderecoController.deletar(1l);
        Assert.assertEquals("Item deletado com sucesso",endereco.getBody());
    }

    @Test
    void Teste8DeletarService(){
        var endereco = enderecoService.deletar(1l);
        Assert.assertEquals(true,endereco);
    }

    @Test
    void teste9findById_fail() {
        Mockito.when(enderecoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assert.assertThrows(ResponseStatusException.class, () -> {
            enderecoController.findById(5L);
        });
    }

    @Test
    void teste10findAll_fail() {
        Mockito.when(enderecoRepository.findAll()).thenReturn(new ArrayList<Endereco>());
        Assert.assertThrows(ResponseStatusException.class, () -> {
            enderecoController.findAll();
        });
    }



}
