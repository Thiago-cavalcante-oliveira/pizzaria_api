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
    static final String SUCESSO = "Operacao realizada com sucesso";
    static final String DELETED = "Item deletado com sucesso";
    static final String DISABLED = "Item inativado com sucesso";
    static final String FAIL = "Falha ao cadastrar endereco";

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
            if (enderecoDTO == null || enderecoDTO.getTelResidencia() == null || enderecoDTO.getTelResidencia().isEmpty()) {
                throw new IllegalArgumentException("O campo 'Telefone' é obrigatório.");
            }
            this.enderecoService.cadastrar(enderecoDTO);

            return ResponseEntity.ok(SUCESSO);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(FAIL);
        }
    }

    @PutMapping
    public ResponseEntity<String> editar(
            @RequestParam("id") final Long id,
            @RequestBody final EnderecoDTO enderecoDTO)
    {
        try {
            this.enderecoService.editar(enderecoDTO, id);

            return ResponseEntity.ok(SUCESSO);
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
                return ResponseEntity.ok(DELETED);
            }
            else{
                return ResponseEntity.ok(DISABLED);
            }
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
