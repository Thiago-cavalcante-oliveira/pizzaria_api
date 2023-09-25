package com.example.pizzaria;

import com.example.pizzaria.error.handler.ErrorHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestMultiply {

   /* ErrorHandler erro = new ErrorHandler();

    @Test
    void testeSet() {
        erro.setCampo("campo");
        erro.setMessage("mensagem");
        Assertions.assertEquals("mensagem", erro.getMessage());
        Assertions.assertEquals("campo", erro.getCampo());
    }
 @Test
    void testeConstrutor(){
        ErrorHandler erro1 = new ErrorHandler("teste","teste");
        Assertions.assertEquals("teste", erro1.getCampo());
 }

 @Test
    void testeHash(){
     ErrorHandler erro1 = new ErrorHandler("teste","teste");
     ErrorHandler erro2 = new ErrorHandler();
     erro2.setMessage("falha");
     erro2.setCampo("falha");

     Assertions.assertNotEquals(erro1.getMessage(),erro2.getMessage());
     Assertions.assertNotEquals(erro1.getCampo(), erro2.getCampo());
 }*/

}
