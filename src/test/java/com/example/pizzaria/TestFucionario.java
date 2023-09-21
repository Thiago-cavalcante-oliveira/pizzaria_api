package com.example.pizzaria;

import com.example.pizzaria.controller.FuncionarioController;
import com.example.pizzaria.dto.ClienteDTO;
import com.example.pizzaria.dto.FuncionarioDTO;
import com.example.pizzaria.entity.Cliente;
import com.example.pizzaria.entity.Funcionario;
import com.example.pizzaria.repository.FuncionarioRepository;
import com.example.pizzaria.service.FuncionarioService;
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
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        assertEquals(1L, Objects.requireNonNull(funcionario.getBody()).getId(), 0);
    }

    @Test
    void Teste2FindByIdService(){
        var funcionario = funcionarioService.findById(1L);
        assertEquals(1L, funcionario.getId(), 0);
    }

    @Test
    void Teste3FindByAll(){
        var funcionario = funcionarioController.findAll();
        assertEquals(1, funcionario.getBody().size());
    }
    @Test
    void Teste4FindByAllService(){
        var funcionario = funcionarioService.findAll();
        assertEquals(1, funcionario.size());
    }

    @Test
    void Teste5CadastrarFuncionario(){
        FuncionarioDTO funcionarioDTO = criaFuncionarioDto(criaFuncionario());
        var funcionario = funcionarioController.cadastrar(funcionarioDTO);
        assertEquals("Operacao realizada com sucesso", funcionario.getBody());
    }

    @Test
    void Teste6Atualizar(){
        FuncionarioDTO funcionarioDTO = criaFuncionarioDto(criaFuncionario());
        var funcionario = funcionarioController.editar(1L, funcionarioDTO);
        assertEquals(200, funcionario.getStatusCodeValue());
    }


    @Test
    void Teste7Deletar(){
        var funcionario = funcionarioController.deletar(1l);
        assertEquals("Item inativado com sucesso", funcionario.getBody());
    }


    @Test
    void teste9findById_fail() {
        Mockito.when(funcionarioRepository.findById(5L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            funcionarioController.findById(5L);
        });
    }

    @Test
    void teste10findAll_fail() {
        Mockito.when(funcionarioRepository.findAll()).thenReturn(new ArrayList<Funcionario>());
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            funcionarioController.findAll();
        });
    }

    @Test
    void teste11CadastrarFail() {
        FuncionarioDTO funcionarioDTO = null;
        assertThrows(IllegalArgumentException.class, () -> funcionarioController.cadastrar(funcionarioDTO));
    }



    @Test
    void teste12DeletarFail()
    {
        Mockito.when(funcionarioRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> funcionarioController.deletar(1L));
    }

    @Test
    void teste13AtualizarFailIdDiferentes() {
        FuncionarioDTO funcionarioDTO = criaFuncionarioDto(criaFuncionario());
        assertThrows(ResponseStatusException.class, () ->  funcionarioController.editar(2l, funcionarioDTO));

    }

    @Test
    void teste14AtualizarSucess() {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setId(1L);
        funcionarioDTO.setNome("Eduardo");
        funcionarioDTO.setCpf("109.999.888-78");
        funcionarioDTO.setFuncao("Gerente");
        funcionarioDTO.setAtivo(true);
        Mockito.when(funcionarioRepository.isTheSame(Mockito.anyString())).thenReturn(1l);
        Mockito.when(funcionarioRepository.alreadyExists(Mockito.anyString())).thenReturn(true);
        var teste = funcionarioController.editar(1l, funcionarioDTO);
        Assert.assertTrue(teste.getBody().contains("sucesso"));

    }

    @Test
    void teste15AtualizarFailDuplicated() {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setId(5L);
        funcionarioDTO.setNome("Eduardo");
        funcionarioDTO.setCpf("109.999.888-78");
        funcionarioDTO.setFuncao("Gerente");
        funcionarioDTO.setAtivo(true);
        Mockito.when(funcionarioRepository.isTheSame(Mockito.anyString())).thenReturn(1l);
        Mockito.when(funcionarioRepository.alreadyExists(Mockito.anyString())).thenReturn(true);
        assertThrows(ResponseStatusException.class, () -> funcionarioController.editar(5l, funcionarioDTO));

    }

    @Test
    void teste16CadastrarFail() {
        Mockito.when(funcionarioRepository.alreadyExists(Mockito.anyString())).thenReturn(true);
        FuncionarioDTO funcionarioDTO = criaFuncionarioDto(criaFuncionario());
        assertThrows(IllegalArgumentException.class, () -> funcionarioController.cadastrar(funcionarioDTO));
    }

    @Test
    void teste17GetSetNome() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Eduardo");
        assertEquals("Eduardo", funcionario.getNome());
    }

    @Test
    void teste18GetSetCpf() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf("123.456.789-00");
        assertEquals("123.456.789-00", funcionario.getCpf());
    }

    @Test
    void teste19GetSetFuncao() {
        Funcionario funcionario = new Funcionario();
        funcionario.setFuncao("Gerente");
        assertEquals("Gerente", funcionario.getFuncao());
    }

    @Test
    void teste20GetSetNomeDTO() {
        FuncionarioDTO funcionario = new FuncionarioDTO();
        funcionario.setNome("Eduardo");
        assertEquals("Eduardo", funcionario.getNome());
    }

    @Test
    void teste21GetSetCpfDTO() {
        FuncionarioDTO funcionario = new FuncionarioDTO();
        funcionario.setCpf("123.456.789-00");
        assertEquals("123.456.789-00", funcionario.getCpf());
    }

    @Test
    void teste22GetSetFuncaoDTO() {
        FuncionarioDTO funcionario = new FuncionarioDTO();
        funcionario.setFuncao("Gerente");
        assertEquals("Gerente", funcionario.getFuncao());
    }


    @Test
    void teste23HashDto() {

        FuncionarioDTO funcionario = new FuncionarioDTO();
        funcionario.setId(1L);
        funcionario.setNome("Eduardo");
        funcionario.setCpf("109.999.888-78");
        funcionario.setFuncao("Gerente");

        FuncionarioDTO funcionario2 = new FuncionarioDTO();
        funcionario2.setId(1L);
        funcionario2.setNome("Eduardo souza");
        funcionario2.setCpf("109.429.688-78");
        funcionario2.setFuncao("Atendente");


        assertNotEquals(funcionario, funcionario2);

        assertNotEquals(funcionario.hashCode(), funcionario.hashCode());
    }

    @Test
    void teste23HashEntity() {

        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Eduardo");
        funcionario.setCpf("109.999.888-78");
        funcionario.setFuncao("Gerente");

        FuncionarioDTO funcionario2 = new FuncionarioDTO();
        funcionario2.setId(1L);
        funcionario2.setNome("Eduardo souza");
        funcionario2.setCpf("109.429.688-78");
        funcionario2.setFuncao("Atendente");


        assertNotEquals(funcionario, funcionario2);

        assertNotEquals(funcionario.hashCode(), funcionario.hashCode());
    }

}
