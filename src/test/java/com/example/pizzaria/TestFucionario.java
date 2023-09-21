package com.example.pizzaria;

import com.example.pizzaria.controller.FuncionarioController;
import com.example.pizzaria.dto.FuncionarioDTO;
import com.example.pizzaria.entity.Cliente;
import com.example.pizzaria.entity.Funcionario;
import com.example.pizzaria.repository.FuncionarioRepository;
import com.example.pizzaria.service.FuncionarioService;
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
    void Teste1FindById(){
        var funcionario = funcionarioController.findById(1l);
        Assert.assertEquals(1L, funcionario.getBody().getId(), 0);
    }

    @Test
    void Teste2FindByIdService(){
        var funcionario = funcionarioService.findById(1l);
        Assert.assertEquals(1L, funcionario.getId(), 0);
    }

    @Test
    void Teste3FindByAll(){
        var funcionario = funcionarioController.findAll();
        Assert.assertEquals(1, funcionario.getBody().size());
    }
    @Test
    void Teste4FindByAllService(){
        var funcionario = funcionarioService.findAll();
        Assert.assertEquals(1, funcionario.size());
    }

    @Test
    void Teste5CadastrarFuncionario(){
        FuncionarioDTO funcionarioDTO = criaFuncionarioDto(criaFuncionario());
        var funcionario = funcionarioController.cadastrar(funcionarioDTO);
        Assert.assertEquals("Operacao realizada com sucesso", funcionario.getBody());
    }

    @Test
    void Teste6Atualizar(){
        FuncionarioDTO funcionarioDTO = criaFuncionarioDto(criaFuncionario());
        var funcionario = funcionarioController.editar(1L, funcionarioDTO);
        Assert.assertEquals(200, funcionario.getStatusCodeValue());
    }


    @Test
    void Teste7Deletar(){
        var funcionario = funcionarioController.deletar(1l);
        Assert.assertEquals("Item inativado com sucesso",funcionario.getBody());
    }
    @Test
    void Teste8DeletarService(){
        var funcionario = funcionarioService.deletar(1l);
        Assert.assertEquals(true,funcionario);
    }

    @Test
    void teste9findById_fail() {
        Mockito.when(funcionarioRepository.findById(5L)).thenReturn(Optional.empty());
        Assert.assertThrows(ResponseStatusException.class, () -> {
            funcionarioController.findById(5L);
        });
    }

    @Test
    void teste10findAll_fail() {
        Mockito.when(funcionarioRepository.findAll()).thenReturn(new ArrayList<Funcionario>());
        Assert.assertThrows(ResponseStatusException.class, () -> {
            funcionarioController.findAll();
        });
    }

}
