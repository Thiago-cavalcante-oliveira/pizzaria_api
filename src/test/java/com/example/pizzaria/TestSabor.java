package com.example.pizzaria;


import com.example.pizzaria.controller.SaborController;
import com.example.pizzaria.dto.SaborDTO;
import com.example.pizzaria.entity.Sabor;
import com.example.pizzaria.repository.SaborRepository;
import com.example.pizzaria.service.SaborService;
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

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestSabor {

    @MockBean
    SaborRepository saborRepository;
    @Autowired
    SaborService saborService;
    @Autowired
    SaborController saborController;
    static
    ModelMapper modelMapper = new ModelMapper();

    protected static Sabor criaSabor() {
        Sabor sabor = new Sabor();
        sabor.setId(1L);
        sabor.setNomeSabor("Calabresa");
        sabor.setValor(10.00);
        sabor.setIngredientes("Calabresa, queijo, molho de tomate");
        return sabor;
    }

    protected static List<Sabor> listaSabores(Sabor sabor) {
        List<Sabor> sabores = new ArrayList<>();
        sabores.add(sabor);
        return sabores;
    }

    protected static SaborDTO criaSaborDTO(Sabor sabor) {
        return modelMapper.map(sabor, SaborDTO.class);
    }

    @BeforeEach
    void injectDados() {
        Sabor sabor = new Sabor();
        sabor.setId(1L);
        sabor.setNomeSabor("Calabresa");
        sabor.setValor(10.00);
        sabor.setIngredientes("Calabresa, queijo, molho de tomate");
        SaborDTO saborDTO = modelMapper.map(sabor, SaborDTO.class);

        Mockito.when(saborRepository.findById(criaSabor().getId())).thenReturn(Optional.of(criaSabor()));
        Mockito.when(saborRepository.findByNome(saborDTO.getNome())).thenReturn(criaSabor());
        Mockito.when(saborRepository.findAll()).thenReturn(listaSabores(criaSabor()));
        Mockito.when(saborRepository.save(criaSabor())).thenReturn(criaSabor());
    }

    @Test
    void Teste1FindByIDSuccess() {
        var sabor = saborController.findById(1L);
        Assertions.assertEquals(1, sabor.getBody().getId(), 0);
    }

    @Test
    void teste2FindByIdFail() {
        Mockito.when(saborRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> saborController.findById(1L));
    }

    @Test
    void teste3FindAllSuccess() {
        var sabores = saborController.findAll();
        Assertions.assertEquals(1, sabores.getBody().size(), 0);
    }

    @Test
    void teste4FindAllFailMessage() {
        Mockito.when(saborRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> saborController.findAll());
        Assertions.assertTrue(exception.getMessage().contains("Sabor não cadastrado"));
    }

    @Test
    void teste5FindAllFailException() {
        Mockito.when(saborRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ResponseStatusException.class, () -> saborController.findAll());
    }

    @Test
    void teste6CadastrarSuccess() {
        SaborDTO saborDTO = criaSaborDTO(criaSabor());
        var sabor = saborController.cadastrar(saborDTO);
        Assert.assertEquals("Sabor cadastrado com sucesso", sabor.getBody());
    }

    @Test
    void teste7CadastrarFail() {
        SaborDTO saborDTO = criaSaborDTO(criaSabor());
        Mockito.when(saborRepository.existsByNome(Mockito.anyString())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> saborController.cadastrar(saborDTO));
        Assertions.assertTrue(exception.getMessage().contains("Sabor já cadastrado"));
    }

    @Test
    void teste11CadastrarControllerCatch() {
        SaborDTO saborDTO = criaSaborDTO(criaSabor());
        Mockito.when(saborRepository.existsByNome(Mockito.anyString())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> saborController.cadastrar(saborDTO));
        Assertions.assertTrue(exception.getMessage().contains("Sabor já cadastrado"));

    }

    @Test
    void teste8AtualizarSuccess() {
        SaborDTO saborDTO = criaSaborDTO(criaSabor());
        var sabor = saborController.editar(1l, saborDTO);
        Assert.assertEquals(200, sabor.getStatusCodeValue());
    }


    @Test
    void teste9AtualizarFailIdDiferentes() {
        SaborDTO saborDTO = criaSaborDTO(criaSabor());
        Mockito.when(saborRepository.existsByNome(Mockito.anyString())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> saborController.editar(5L, saborDTO));
        Assertions.assertTrue(exception.getMessage().contains("coincidem"));
    }


    @Test
    void teste10AtualizarFailDuplicated() {
        SaborDTO saborDTO = criaSaborDTO(criaSabor());
        saborDTO.setId(5L);
        Mockito.when(saborRepository.findByNome(Mockito.anyString())).thenReturn(criaSabor());
        ResponseStatusException exceptio = assertThrows(ResponseStatusException.class,
                () -> saborController.editar(1L, saborDTO));
        Assertions.assertFalse(exceptio.getMessage().contains("Sabor já cadastrado"));
    }


    @Test
    void teste12DesativarSuccess() {
        Mockito.when(saborRepository.saborExistTb_pizza(Mockito.anyLong())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> saborController.deletar(1L));
        Assertions.assertTrue(exception.getMessage().contains("Sabor desativado"));
    }
    @Test
    void teste12DeletarSuccess() {
        Mockito.when(saborRepository.saborExistTb_pizza(Mockito.anyLong())).thenReturn(false);
        Mockito.when(saborRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(criaSabor()));
        Assertions.assertFalse(saborController.deletar(5L).getBody().contains("Tipo de pizza deletado com sucesso"));
    }
    @Test
    void testEqualsAndHashCodeExcludesSuperclassField() {
        // Criar duas instâncias de SaborDTO com o mesmo valor para um campo herdado (se houver)
        Sabor sabor1 = new Sabor();
        sabor1.setId(1L); // Suponhamos que a classe pai (superclasse) tenha um campo "id"
        sabor1.setNomeSabor("Pizza Margherita");

        Sabor sabor2 = new Sabor();
        sabor2.setId(1L); // Mesmo valor para o campo herdado
        sabor2.setNomeSabor("Pizza Calabresa");

        // Verificar que as instâncias não são iguais
        assertNotEquals(sabor1, sabor2);

        // Verificar que os códigos hash são diferentes
        assertNotEquals(sabor1.hashCode(), sabor2.hashCode());
    }

    @Test
    void testAllArgsConstructor() {

        Sabor sabor = new Sabor("Pizza Margherita", "Ingredientes da Margherita", 12.99);


        assertNotNull(sabor);
        assertEquals("Pizza Margherita", sabor.getNomeSabor());
        assertEquals("Ingredientes da Margherita", sabor.getIngredientes());
        assertEquals(12.99, sabor.getValor(), 0.001); // Use uma margem de erro pequena para valores double
    }

    @Test
    void testEquals() {
        Sabor sabor1 = new Sabor("Pizza Margherita", "Ingredientes da Margherita", 12.99);
        Sabor sabor2 = new Sabor("Pizza Margherita", "Ingredientes da Margherita", 12.99);
        SaborDTO saborDTO1 = new SaborDTO("Pizza Margherita", "Ingredientes da Margherita", 12.99);
        SaborDTO saborDTO2 = new SaborDTO("Pizza Margherita", "Ingredientes da Margherita", 12.99);

        assertTrue(sabor1.equals(sabor2));
        assertTrue(sabor2.equals(sabor1));
        assertTrue(saborDTO1.equals(saborDTO2));
        assertTrue(saborDTO2.equals(saborDTO1));

        Sabor sabor3 = new Sabor("Pizza Calabresa", "Ingredientes da Calabresa", 13.99);
        Sabor sabor4 = new Sabor("Pizza Pepperoni", "Ingredientes do Pepperoni", 11.99);
        SaborDTO saborDTO3 = new SaborDTO("Pizza Pepperoni", "Ingredientes do Pepperoni", 11.99);
        SaborDTO saborDTO4 = new SaborDTO("Pizza Pepperoni", "Ingredientes do Pepperoni", 15.99);

        assertFalse(sabor1.equals(sabor3));
        assertFalse(sabor1.equals(sabor4));
        assertFalse(sabor3.equals(sabor4));
        assertTrue(saborDTO3.equals(saborDTO4));
        assertTrue(saborDTO4.equals(saborDTO3));


    }
}










