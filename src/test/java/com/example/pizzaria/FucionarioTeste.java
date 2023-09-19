package com.example.pizzaria;

import com.example.pizzaria.controller.FuncionarioController;
import com.example.pizzaria.dto.ClienteDTO;
import com.example.pizzaria.dto.EnderecoDTO;
import com.example.pizzaria.dto.FuncionarioDTO;
import com.example.pizzaria.entity.Funcionario;
import com.example.pizzaria.repository.FuncionarioRepository;
import com.example.pizzaria.service.FuncionarioService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
 class FucionarioTeste {
    @MockBean
    FuncionarioRepository funcionarioRepository;

    @Autowired
    FuncionarioController funcionarioController;

    @Autowired
    FuncionarioService funcionarioService;


    @BeforeEach
    void injectDados(){
        Funcionario funcionario = new Funcionario();

        funcionario.setId(1l);
        funcionario.setNome("Eduardo");
        funcionario.setCpf("109.999.888-78");
        funcionario.setFuncao("Gerente");
        List<Funcionario> funcionarioList = new ArrayList<>();
        funcionarioList.add(funcionario);

        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();

        funcionarioDTO.setId(funcionario.getId());
        funcionarioDTO.setNome(funcionario.getNome());
        funcionarioDTO.setCpf(funcionario.getCpf());
        funcionarioDTO.setFuncao(funcionario.getFuncao());
        List<FuncionarioDTO> funcionarioDTOList = new ArrayList<>();
        funcionarioDTOList.add(funcionarioDTO);

        Mockito.when(funcionarioRepository.findById(funcionario.getId())).thenReturn(Optional.of(funcionario));
        Mockito.when(funcionarioRepository.findAll()).thenReturn(funcionarioList);
        Mockito.when(funcionarioRepository.alreadyExists(funcionario.getCpf())).thenReturn(true);
        Mockito.when(funcionarioRepository.isTheSame(funcionario.getCpf())).thenReturn(1l);
        Mockito.when(funcionarioRepository.doesExist(funcionario.getId())).thenReturn(funcionario.isAtivo());
    }

    @Test
    void TesteFindById(){
        var funcionario = funcionarioController.findById(1l);
        Assert.assertEquals(1l, funcionario.getBody().getId(), 0);
    }

    @Test
    void TesteFindByAll(){
        var funcionario = funcionarioController.findAll();
        Assert.assertEquals(1, funcionario.getBody().size());
    }

    @Test
    void TesteCadastrarFuncionario(){
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO("Eduardo Souza", "109.999.777-88","Gerente" );
        var funcionario = funcionarioController.cadastrar(funcionarioDTO);
        Assert.assertEquals("Funcionario cadastrado com sucesso", funcionario.getBody());
    }

    @Test
    void TesteAtualizar(){
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();

        funcionarioDTO.setId(1l);
        funcionarioDTO.setNome("Eduardo");
        funcionarioDTO.setCpf("109.999.777-88");
        funcionarioDTO.setFuncao("Gerente");
        List<FuncionarioDTO> funcionarioDTOList = new ArrayList<>();
        funcionarioDTOList.add(funcionarioDTO);

        var funcionario = funcionarioController.editar(1l, funcionarioDTO);
        Assert.assertEquals(200, funcionario.getStatusCodeValue());
    }


    @Test
    void TesteDeletar(){
        var funcionario = funcionarioController.deletar(1l);
        Assert.assertEquals("Funcionario desativado",funcionario.getBody());
    }

}
