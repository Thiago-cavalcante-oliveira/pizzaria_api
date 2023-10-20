package com.example.pizzaria.controller;

import com.example.pizzaria.dto.PizzaDTO;
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

@PostMapping
    public ResponseEntity<String> cadastrar(@Validated @RequestBody final PizzaDTO pizza){
        try{
            return ResponseEntity.ok(this.pizzaService.cadastrar(pizza));
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> editar(@Validated @RequestParam("id") final Long id, @RequestBody final PizzaDTO pizza){

        try{
            return ResponseEntity.ok( this.pizzaService.editar(pizza, id));
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletar(@RequestParam("id") final Long id){
        try{
            String msg =  this.pizzaService.deletar(id);
            return ResponseEntity.ok(msg);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
