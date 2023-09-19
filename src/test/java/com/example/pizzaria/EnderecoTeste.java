package com.example.pizzaria;

import com.example.pizzaria.controller.EnderecoController;
import com.example.pizzaria.dto.ClienteDTO;
import com.example.pizzaria.dto.EnderecoDTO;
import com.example.pizzaria.entity.Cliente;
import com.example.pizzaria.entity.Endereco;
import com.example.pizzaria.repository.EnderecoRepository;
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
public class EnderecoTeste {
    @MockBean
    EnderecoRepository enderecoRepository;
    @Autowired
    EnderecoController enderecoController;

    @Autowired
    ModelMapper modelMapper;

    protected Endereco criaEndereco()
    {
        Endereco endereco = new Endereco();
        endereco.setId(1l);
        endereco.setCep("85858-330");
        endereco.setNuEndereco(1445);
        endereco.setRua("Jose epinafio teles Costa");
        endereco.setBairro("Morumbi");
        endereco.setTelResidencia("45 99999-8855");
        endereco.setComplemento("casa");
        //endereco.setCliente(cliente);

        return endereco;
    }

    protected List<Endereco> listaEndereco()
    {
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(criaEndereco());

        return enderecos;
    }

    protected EnderecoDTO criaEnderecoDTO(Endereco endereco)
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
    void TesteFindById(){
        var endereco = enderecoController.findById(1l);
        Assert.assertEquals(1l, endereco.getBody().getId(), 0);
    }

    @Test
    void TesteFindByAll(){
        var endereco = enderecoController.findAll();
        Assert.assertEquals(1, endereco.getBody().size());

    }

    @Test
    void TesteCadastrarEndereco(){
        var endereco = enderecoController.cadastrar(criaEnderecoDTO(criaEndereco()));
        Assert.assertEquals("Endereço cadastrado com sucesso", endereco.getBody());
    }

    @Test
    void TesteAtualizar(){

        var endereco = enderecoController.editar(1l, criaEnderecoDTO(criaEndereco()));
        Assert.assertEquals(200, endereco.getStatusCodeValue());
    }


    @Test
    void TesteDeletar(){
        var endereco = enderecoController.deletar(1l);
        Assert.assertEquals("Endereço desativado",endereco.getBody());
    }



}
