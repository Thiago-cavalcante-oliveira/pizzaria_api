package com.example.pizzaria.controller;

import com.example.pizzaria.dto.PizzaDTO;
import com.example.pizzaria.dto.Resposta;
import com.example.pizzaria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pizza")
@CrossOrigin(origins = "http://localhost:4200")
public class PizzaController {

    @Autowired
    PizzaService pizzaService;

    @GetMapping
    public ResponseEntity<PizzaDTO> findById(@Validated @RequestParam("id") final Long id){
        try{
            return ResponseEntity.ok(pizzaService.findById(id));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<PizzaDTO>> findAll(){
            return ResponseEntity.ok(pizzaService.findAll());
}

    @GetMapping("/ativo")
    public ResponseEntity<List<PizzaDTO>> findAllAtivo(){
        return ResponseEntity.ok(pizzaService.findAllAtivo());
    }


    @PostMapping
    public ResponseEntity<PizzaDTO> cadastrar(@Validated @RequestBody final PizzaDTO pizza){
        try{
            return ResponseEntity.ok(this.pizzaService.cadastrar(pizza));
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<PizzaDTO> editar(@Validated @RequestParam("id") final Long id, @RequestBody final PizzaDTO pizza){

        try{
            return ResponseEntity.ok( this.pizzaService.editar(pizza, id));
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Resposta> deletar(@RequestParam("id") final Long id){
        try{
            Resposta resposta = new Resposta();
            resposta.setMensangem(this.pizzaService.deletar(id));
            return ResponseEntity.ok(resposta);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
