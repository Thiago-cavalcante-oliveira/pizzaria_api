package com.example.pizzaria.controller;

import com.example.pizzaria.dto.ClienteDTO;
import com.example.pizzaria.dto.Resposta;
import com.example.pizzaria.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "api/cliente")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {


    @Autowired
    private ClienteService clienteService;

    static final String SUCESSO = "Operação realizada com sucesso";
    static final String DISABLE = "Cliente desativado";

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
    public ResponseEntity<ClienteDTO> cadastrar(@RequestBody final ClienteDTO clienteDTO){

        try{


            return ResponseEntity.ok(this.clienteService.cadastrar(clienteDTO));
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<ClienteDTO> editar(
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
    public ResponseEntity<Resposta> deletar(@RequestParam("id") final Long id)
    {
        try {
            Resposta resposta = new Resposta();
            resposta.setMensangem(this.clienteService.deletar(id));
            return ResponseEntity.ok(resposta);


        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
