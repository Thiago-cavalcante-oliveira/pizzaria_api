package com.example.pizzaria.controller;

import com.example.pizzaria.dto.PizzaTipoDTO;
import com.example.pizzaria.repository.PizzaTipoRepository;
import com.example.pizzaria.repository.ProdutoDiversoRepositorio;
import com.example.pizzaria.service.PizzaTipoService;
import com.example.pizzaria.service.ProdutoDiversoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "api/pizza_tipo")
public class PizzaTipoController {

    @Autowired
    private PizzaTipoService pizzaTipoService;

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody final PizzaTipoDTO pizzaTipoDTO) {
        try {
            return ResponseEntity.ok( this.pizzaTipoService.cadastrar(pizzaTipoDTO));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> editar(@RequestParam("id") final Long id, @RequestBody final PizzaTipoDTO pizzaTipoDTO) {
        try {
            return ResponseEntity.ok(this.pizzaTipoService.editar(pizzaTipoDTO, id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<PizzaTipoDTO>> findAll() {
        try {
            return ResponseEntity.ok(this.pizzaTipoService.findAll());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<PizzaTipoDTO> buscar(@RequestParam("id") final Long id) {
        try {
            return ResponseEntity.ok(this.pizzaTipoService.findById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletar(@RequestParam("id") final Long id) {
        try {
            return ResponseEntity.ok(this.pizzaTipoService.deletar(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}
