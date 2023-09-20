package com.example.pizzaria;

import com.example.pizzaria.controller.FuncionarioController;
import com.example.pizzaria.dto.FuncionarioDTO;
import com.example.pizzaria.entity.Funcionario;
import com.example.pizzaria.repository.FuncionarioRepository;
import com.example.pizzaria.service.FuncionarioService;
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
 class TestFucionario {
    @MockBean
    FuncionarioRepository funcionarioRepository;

    @Autowired
    FuncionarioController funcionarioController;

    @Autowired
    FuncionarioService funcionarioService;

    static
    ModelMapper modelMapper = new ModelMapper();


    protected static Funcionario criaFuncionario(){
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Eduardo");
        funcionario.setCpf("109.999.888-78");
        funcionario.setFuncao("Gerente");
        return  funcionario;
    }

    protected static List<Funcionario> listaFuncionario(){
        List<Funcionario> funcionarioList = new ArrayList<>();
        funcionarioList.add(criaFuncionario());
        return funcionarioList;
    }

    protected static FuncionarioDTO criaFuncionarioDto(Funcionario funcionario){
        return modelMapper.map(funcionario, FuncionarioDTO.class);
    }
    @BeforeEach
    void injectDados(){

        Mockito.when(funcionarioRepository.findById(criaFuncionario().getId())).thenReturn(Optional.of(criaFuncionario()));
        Mockito.when(funcionarioRepository.findAll()).thenReturn(listaFuncionario());
        Mockito.when(funcionarioRepository.isTheSame(criaFuncionario().getCpf())).thenReturn(1L);
        Mockito.when(funcionarioRepository.doesExist(criaFuncionario().getId())).thenReturn(criaFuncionario().isAtivo());
        Mockito.when(funcionarioRepository.alreadyExists(criaFuncionario().getCpf())).thenReturn(false);
    }

    @Test
    void TesteFindById(){
        var funcionario = funcionarioController.findById(1l);
        Assert.assertEquals(1L, funcionario.getBody().getId(), 0);
    }

    @Test
    void TesteFindByAll(){
        var funcionario = funcionarioController.findAll();
        Assert.assertEquals(1, funcionario.getBody().size());
    }

    @Test
    void TesteCadastrarFuncionario(){
        FuncionarioDTO funcionarioDTO = criaFuncionarioDto(criaFuncionario());
        var funcionario = funcionarioController.cadastrar(funcionarioDTO);
        Assert.assertEquals("Operacao realizada com sucesso", funcionario.getBody());
    }

    @Test
    void TesteAtualizar(){
        FuncionarioDTO funcionarioDTO = criaFuncionarioDto(criaFuncionario());
        var funcionario = funcionarioController.editar(1L, funcionarioDTO);
        Assert.assertEquals(200, funcionario.getStatusCodeValue());
    }


    @Test
    void TesteDeletar(){
        var funcionario = funcionarioController.deletar(1l);
        Assert.assertEquals("Item inativado com sucesso",funcionario.getBody());
    }

}
