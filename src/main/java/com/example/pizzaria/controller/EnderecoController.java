package com.example.pizzaria.controller;


import com.example.pizzaria.dto.EnderecoDTO;
import com.example.pizzaria.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;
    final String success = "Operacao realizada com sucesso";
    final String deleted = "Item deletado com sucesso";
    final String disabled = "Item inativado com sucesso";

    @GetMapping("all")
    public ResponseEntity<List<EnderecoDTO>> findAll()
    {
        try{
            return ResponseEntity.ok(this.enderecoService.findAll());
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<EnderecoDTO> findById(@RequestParam("id") final Long id){
        try{
            return ResponseEntity.ok(this.enderecoService.findById(id));
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody final EnderecoDTO enderecoDTO){
        try{
            this.enderecoService.cadastrar(enderecoDTO);

            return ResponseEntity.ok(success);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> editar(
            @RequestParam("id") final Long id,
            @RequestBody final EnderecoDTO enderecoDTO)
    {
        try {
            this.enderecoService.editar(enderecoDTO, id);

            return ResponseEntity.ok(success);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletar(@RequestParam("id") final Long id)
    {
        try {
            if(this.enderecoService.deletar(id)){
                return ResponseEntity.ok(deleted);
            }
            else{
                return ResponseEntity.ok(disabled);
            }
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
