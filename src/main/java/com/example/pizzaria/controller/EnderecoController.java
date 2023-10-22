package com.example.pizzaria.controller;


import com.example.pizzaria.dto.EnderecoDTO;
import com.example.pizzaria.dto.Resposta;
import com.example.pizzaria.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/endereco")
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("cliente")
    public ResponseEntity<List<EnderecoDTO>> findByCliente(@RequestParam("id") final Long id)
    {
        try{
            return ResponseEntity.ok(this.enderecoService.findByCliente(id));
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
    public ResponseEntity<EnderecoDTO> cadastrar(@RequestBody final EnderecoDTO enderecoDTO){
        try{



            return ResponseEntity.ok(this.enderecoService.cadastrar(enderecoDTO));
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(FAIL);
        }
    }

    @PutMapping
    public ResponseEntity<EnderecoDTO> editar(
            @RequestParam("id") final Long id,
            @RequestBody final EnderecoDTO enderecoDTO)
    {
        try {


            return ResponseEntity.ok(this.enderecoService.editar(enderecoDTO, id));
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
            resposta.setMensangem(this.enderecoService.deletar(id));
            return ResponseEntity.ok(resposta);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
