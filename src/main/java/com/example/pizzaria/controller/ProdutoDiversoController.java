package com.example.pizzaria.controller;

import com.example.pizzaria.dto.ProdutoDiversoDTO;
import com.example.pizzaria.repository.ProdutoDiversoRepositorio;
import com.example.pizzaria.service.ProdutoDiversoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "api/produto_diverso")
public class ProdutoDiversoController {

    @Autowired
    private ProdutoDiversoService produtoDiversoService;
    @Autowired
    private ProdutoDiversoRepositorio produtoDiversoRepositorio;
    static final String success = "Operacao realizada com sucesso";
    static final String deleted = "Item deletado com sucesso";

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody final ProdutoDiversoDTO produtoDiversoDTO) {

        try {
            this.produtoDiversoService.cadastrar(produtoDiversoDTO);

            return ResponseEntity.ok(success);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<String> editar(@RequestParam("id") final Long id, @RequestBody final ProdutoDiversoDTO produtoDiversoDTO) {

        try {
            this.produtoDiversoService.editar(produtoDiversoDTO, id);

            return ResponseEntity.ok(success);
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

    @GetMapping
    public ResponseEntity<ProdutoDiversoDTO> findById(@RequestParam("id") final Long id) {

        try {
            return ResponseEntity.ok(this.produtoDiversoService.findById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletar(@RequestParam("id") final Long id) {
        try {
            this.produtoDiversoRepositorio.deleteById(id);
            return ResponseEntity.ok(deleted);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
