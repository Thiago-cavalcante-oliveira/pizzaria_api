package com.example.pizzaria.controller;

import com.example.pizzaria.dto.ProdutoDiversoDTO;
import com.example.pizzaria.dto.Resposta;
import com.example.pizzaria.service.ProdutoDiversoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "api/produto_diverso")
@CrossOrigin(origins = "http://localhost:4200")
public class ProdutoDiversoController {

    @Autowired
    private ProdutoDiversoService produtoDiversoService;
    static final String SUCCESS = "Operacao realizada com sucesso";
    static final String DELETED = "Item deletado com sucesso";

    @PostMapping
    public ResponseEntity<ProdutoDiversoDTO> cadastrar(@RequestBody final ProdutoDiversoDTO produtoDiversoDTO) {

        try {


            return ResponseEntity.ok(this.produtoDiversoService.cadastrar(produtoDiversoDTO));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<ProdutoDiversoDTO> editar(@RequestParam("id") final Long id, @RequestBody final ProdutoDiversoDTO produtoDiversoDTO) {

        try {


            return ResponseEntity.ok(this.produtoDiversoService.editar(produtoDiversoDTO, id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<ProdutoDiversoDTO>> findAll() {
        try {
            return ResponseEntity.ok(this.produtoDiversoService.findAll());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("ativo")
    public ResponseEntity<List<ProdutoDiversoDTO>> findAllAtivo() {
        try {
            return ResponseEntity.ok(this.produtoDiversoService.findAllAtivo());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<ProdutoDiversoDTO> findById(@RequestParam("id") final Long id) {
        try {
            return ResponseEntity.ok(this.produtoDiversoService.findById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Resposta> deletar(@RequestParam("id") final Long id) {
        try {
            Resposta resposta = new Resposta();
            resposta.setMensangem(this.produtoDiversoService.deletar(id));
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
