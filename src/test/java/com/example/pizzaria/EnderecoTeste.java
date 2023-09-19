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

    @BeforeEach
    void injectDados(){
        Endereco endereco = new Endereco();
        Cliente cliente = new Cliente("eduardo", "45 99815-6655", "111.888.999-78");
        ClienteDTO clienteDTO = new ClienteDTO("eduardo", "45 99815-6655", "111.888.999-78");

        endereco.setId(1l);
        endereco.setCep("85858-330");
        endereco.setNuEndereco(1445);
        endereco.setRua("Jose epinafio teles Costa");
        endereco.setBairro("Morumbi");
        endereco.setTelResidencia("45 99999-8855");
        endereco.setComplemento("casa");
        endereco.setCliente(cliente);
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco);



        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setId(endereco.getId());
        enderecoDTO.setCep(endereco.getCep());
        enderecoDTO.setNuEndereco(endereco.getNuEndereco());
        enderecoDTO.setRua(endereco.getRua());
        enderecoDTO.setBairro(endereco.getBairro());
        enderecoDTO.setTelResidencia(endereco.getTelResidencia());
        enderecoDTO.setComplemento(endereco.getComplemento());
        enderecoDTO.setCliente(clienteDTO);

        List<EnderecoDTO> enderecoDTOS = new ArrayList<>();
        enderecoDTOS.add(enderecoDTO);

        Mockito.when(enderecoRepository.findById(endereco.getId())).thenReturn(Optional.of(endereco));
        Mockito.when(enderecoRepository.findAll()).thenReturn(enderecos);
        Mockito.when(enderecoRepository.doesExist(endereco.getId())).thenReturn(true);
        Mockito.when(enderecoRepository.save(endereco)).thenReturn(endereco);
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
        EnderecoDTO enderecoDTO = new EnderecoDTO("45 3525-7078", "Jose carlos Paca", 1445, "Morumbi", "85858-963", "Casa", new ClienteDTO("Eduardo", "45 99815-2683", "109.989.963-75") );
        var endereco = enderecoController.cadastrar(enderecoDTO);
        Assert.assertEquals("Endereço cadastrado com sucesso", endereco.getBody());
    }

    @Test
    void TesteAtualizar(){

        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setId(1L);
        enderecoDTO.setCep("85965-895");
        enderecoDTO.setNuEndereco(5);
        enderecoDTO.setRua("eng. araripe");
        enderecoDTO.setBairro("vila a");
        enderecoDTO.setTelResidencia("34 5566-7897");
        enderecoDTO.setComplemento("predio");
        enderecoDTO.setCliente(new ClienteDTO("Eduardo", "45 99815-2683", "109.989.963-75"));

        var endereco = enderecoController.editar(1l, enderecoDTO);
        Assert.assertEquals(200, endereco.getStatusCodeValue());
    }


    @Test
    void TesteDeletar(){
        var endereco = enderecoController.deletar(1l);
        Assert.assertEquals("Endereço desativado",endereco.getBody());
    }



}
