package com.example.pizzaria.controller;

import com.example.pizzaria.dto.FuncionarioDTO;
import com.example.pizzaria.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "api/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;
    @GetMapping("all")
    public ResponseEntity<List<FuncionarioDTO>> findAll()
    {
        try{
            return ResponseEntity.ok(this.funcionarioService.findAll());
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<FuncionarioDTO> findById(@RequestParam("id") final Long id){

        try{
            return ResponseEntity.ok(this.funcionarioService.findById(id));
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody final FuncionarioDTO funcionarioDTO){

        try{
            this.funcionarioService.cadastrar(funcionarioDTO);

            return ResponseEntity.ok("Funcionario cadastrado com sucesso");
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    ResponseEntity<String> editar(
            @RequestParam("id") final Long id,
            @RequestBody final FuncionarioDTO funcionarioDTO)
    {
        try {
            this.funcionarioService.editar(funcionarioDTO, id);

            return ResponseEntity.ok("Funcionario alterado com sucesso");
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping ResponseEntity<String> deletar(@RequestParam("id") final Long id)
    {
        try {
            if(this.funcionarioService.deletar(id)){
                return ResponseEntity.ok("Funcionario desativado");
            }
            else{
                return ResponseEntity.ok("Funcionario deletado");
            }
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
