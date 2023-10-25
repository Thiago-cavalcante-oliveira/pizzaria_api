package com.example.pizzaria.controller;

import com.example.pizzaria.dto.FuncionarioDTO;
import com.example.pizzaria.dto.Resposta;
import com.example.pizzaria.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "api/funcionario")
@CrossOrigin(origins = "http://localhost:4200")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;
    static final String SUCESSO = "Operacao realizada com sucesso";
    static final String DELETED = "Item deletado com sucesso";
    static final String DISABLED = "Item inativado com sucesso";

    static final String FAIL = "Falha ao cadastrar funcionario";

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

    @GetMapping("ativo")
    public ResponseEntity<List<FuncionarioDTO>> findAllAtivo()
    {
        try{
            return ResponseEntity.ok(this.funcionarioService.findAllAtivo());
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
    public ResponseEntity<FuncionarioDTO> cadastrar(@RequestBody final FuncionarioDTO funcionarioDTO){

        try{

            return ResponseEntity.ok(this.funcionarioService.cadastrar(funcionarioDTO));
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(FAIL);
        }
    }

    @PutMapping
    public ResponseEntity<FuncionarioDTO> editar(
            @RequestParam("id") final Long id,
            @RequestBody final FuncionarioDTO funcionarioDTO)
    {
        try {


            return ResponseEntity.ok(this.funcionarioService.editar(funcionarioDTO, id));
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
            resposta.setMensangem(this.funcionarioService.deletar(id));
            return ResponseEntity.ok(resposta);

        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
