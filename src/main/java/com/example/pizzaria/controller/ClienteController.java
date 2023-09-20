package com.example.pizzaria.controller;

import com.example.pizzaria.dto.ClienteDTO;
import com.example.pizzaria.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "api/cliente")
public class ClienteController {


    @Autowired
    private ClienteService clienteService;

    static final String success = "Operação realizada com sucesso";
    static final String fail = "Operação falhou";
    static final String delete = "Cliente deletado";
    static final String disable = "Cliente desativado";

    @GetMapping("all")
    public ResponseEntity<List<ClienteDTO>> findAll()
    {
        try{
            return ResponseEntity.ok(this.clienteService.findAll());
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<ClienteDTO> findById(@RequestParam("id") final Long id){

        try{
            return ResponseEntity.ok(this.clienteService.findById(id));
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody final ClienteDTO clienteDTO){

        try{
            this.clienteService.cadastrar(clienteDTO);

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
            @RequestBody final ClienteDTO clienteDTO)
    {
        try {
            return ResponseEntity.ok( this.clienteService.editar(clienteDTO, id));
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
            if(this.clienteService.deletar(id)){
                return ResponseEntity.ok(disable);
            }
            else{
                return ResponseEntity.ok(delete);
            }
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
