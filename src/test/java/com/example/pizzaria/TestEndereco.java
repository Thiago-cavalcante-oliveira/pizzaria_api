package com.example.pizzaria;

import com.example.pizzaria.controller.EnderecoController;
import com.example.pizzaria.dto.ClienteDTO;
import com.example.pizzaria.dto.EnderecoDTO;
import com.example.pizzaria.entity.Cliente;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

//    @Test
//    void Teste5CadastrarEnderecoController(){
//        var endereco = enderecoController.cadastrar(criaEnderecoDTO(criaEndereco()));
//        Assert.assertEquals("Operacao realizada com sucesso", endereco.getBody());
//    }

//    @Test
//    void Teste6AtualizarController(){
//
//        var endereco = enderecoController.editar(1l, criaEnderecoDTO(criaEndereco()));
//        Assert.assertEquals(200, endereco.getStatusCodeValue());
//    }


//    @Test
//    void Teste7DeletarController(){
//        var endereco = enderecoController.deletar(1l);
//        Assert.assertEquals("Item deletado com sucesso",endereco.getBody());
//    }

//    @Test
//    void Teste8DeletarService(){
//        var endereco = enderecoService.deletar(1l);
//        Assert.assertEquals(true,endereco);
//    }

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


    @Test
    void Teste11AtualizarMalSucedida() {
        EnderecoDTO enderecoDTO = criaEnderecoDTO(criaEndereco());
        Mockito.when(enderecoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            enderecoController.editar(5L, enderecoDTO);
        });
        System.out.println(exception.getMessage());
        Assert.assertTrue(exception.getMessage().contains("Registro não encontrado"));
    }

    @Test
    void Teste12DeleteMalSucedida() {
        Mockito.when(enderecoRepository.doesExist(Mockito.anyLong())).thenReturn(false);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            enderecoController.deletar(5L);
        });
        System.out.println(exception.getMessage());
        Assert.assertTrue(exception.getMessage().contains("Registro não encontrado"));
    }

    @Test
    void Teste13CadastroDadosInvalidos() {
        EnderecoDTO enderecoInvalido = new EnderecoDTO();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            enderecoController.cadastrar(enderecoInvalido);
        });
        System.out.println(exception.getMessage());
        Assert.assertTrue(exception.getMessage().contains("Falha ao cadastrar endereco"));
    }


    @Test
    void teste14GetSetTelResidencia() {
        Endereco endereco = new Endereco();
        endereco.setTelResidencia("1234567890");
        assertEquals("1234567890", endereco.getTelResidencia());
    }

    @Test
    void teste15GetSetRua() {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Teste");
        assertEquals("Rua Teste", endereco.getRua());
    }

    @Test
    void teste16GetSetNuEndereco() {
        Endereco endereco = new Endereco();
        endereco.setNuEndereco(123);
        assertEquals(123, endereco.getNuEndereco());
    }

    @Test
    void teste17GetSetBairro() {
        Endereco endereco = new Endereco();
        endereco.setBairro("Bairro Teste");
        assertEquals("Bairro Teste", endereco.getBairro());
    }

    @Test
    void teste18GetSetCep() {
        Endereco endereco = new Endereco();
        endereco.setCep("12345-678");
        assertEquals("12345-678", endereco.getCep());
    }

    @Test
    void teste19GetSetComplemento() {
        Endereco endereco = new Endereco();
        endereco.setComplemento("Casa");
        assertEquals("Casa", endereco.getComplemento());
    }

    /*@Test
    void teste20HashEntity() {

        Endereco endereco = new Endereco("45 99999-8855","Jose epinafio teles Costa", 1445,"Morumbi", "85858-330","casa",testCliente.criarCliente());

        Endereco endereco1 = new Endereco();
        endereco1.setId(2l);
        endereco1.setCep("85858-990");
        endereco1.setNuEndereco(1495);
        endereco1.setRua("Jose teles Costa");
        endereco1.setBairro("Morumbi IV");
        endereco1.setTelResidencia("45 96669-8855");
        endereco1.setComplemento("Ap");
        endereco1.setCliente(testCliente.criarCliente());


        assertNotEquals(endereco, endereco1);

        assertNotEquals(endereco.hashCode(), endereco1.hashCode());
    }

    @Test
    void teste21HashDto() {

        EnderecoDTO endereco = new EnderecoDTO("45 99999-8855","Jose epinafio teles Costa", 1445,"Morumbi", "85858-330","casa",testCliente.criaClienteDto(testCliente.criarCliente()));

        EnderecoDTO endereco1 = new EnderecoDTO();
        endereco1.setId(2l);
        endereco1.setCep("85858-990");
        endereco1.setNuEndereco(1495);
        endereco1.setRua("Jose teles Costa");
        endereco1.setBairro("Morumbi IV");
        endereco1.setTelResidencia("45 96669-8855");
        endereco1.setComplemento("Ap");
        endereco1.setCliente(testCliente.criaClienteDto(testCliente.criarCliente()));


        assertNotEquals(endereco, endereco1);
        assertNotEquals(endereco.hashCode(), endereco1.hashCode());
    }

    @Test
    void teste22EqualsEntity() {

        Endereco endereco = new Endereco("45 99999-8855","Jose epinafio teles Costa", 1445,"Morumbi", "85858-330","casa",testCliente.criarCliente());
        endereco.setId(2l);
        Endereco endereco1 = new Endereco();
        endereco1.setId(2l);
        endereco1.setCep("85858-330");
        endereco1.setNuEndereco(1445);
        endereco1.setRua("Jose epinafio teles Costa");
        endereco1.setBairro("Morumbi");
        endereco1.setTelResidencia("45 99999-8855");
        endereco1.setComplemento("casa");
        endereco1.setCliente(testCliente.criarCliente());


        assertEquals(endereco, endereco1);

        assertEquals(endereco.hashCode(), endereco1.hashCode());
    }

    @Test
    void teste23EqualsDTO() {

        EnderecoDTO endereco = new EnderecoDTO("45 99999-8855","Jose epinafio teles Costa", 1445,"Morumbi", "85858-330","casa",testCliente.criaClienteDto(TestCliente.criarCliente()));
        endereco.setId(2l);
        EnderecoDTO endereco1 = new EnderecoDTO();
        endereco1.setId(2l);
        endereco1.setCep("85858-330");
        endereco1.setNuEndereco(1445);
        endereco1.setRua("Jose epinafio teles Costa");
        endereco1.setBairro("Morumbi");
        endereco1.setTelResidencia("45 99999-8855");
        endereco1.setComplemento("casa");
        endereco1.setCliente(testCliente.criaClienteDto(TestCliente.criarCliente()));


        assertEquals(endereco, endereco1);

        assertEquals(endereco.hashCode(), endereco1.hashCode());
    }*/


}
