package com.example.pizzaria.controller;

import com.example.pizzaria.dto.ProdutoDiversoDTO;
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
    public ResponseEntity<String> cadastrar(@RequestBody final ProdutoDiversoDTO produtoDiversoDTO) {

        try {
            this.produtoDiversoService.cadastrar(produtoDiversoDTO);

            return ResponseEntity.ok(SUCCESS);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<String> editar(@RequestParam("id") final Long id, @RequestBody final ProdutoDiversoDTO produtoDiversoDTO) {

        try {
            this.produtoDiversoService.editar(produtoDiversoDTO, id);

            return ResponseEntity.ok(SUCCESS);
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
            this.produtoDiversoService.deletar(id);
            return ResponseEntity.ok(DELETED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
