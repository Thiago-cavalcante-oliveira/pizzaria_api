package com.example.pizzaria.controller;

import com.example.pizzaria.dto.SaborDTO;
import com.example.pizzaria.service.SaborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/sabor")
@CrossOrigin(origins = "http://localhost:4200")
public class SaborController {

    @Autowired
    private SaborService saborService;

@PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody final SaborDTO saborDTO){
        try{
            return ResponseEntity.ok( this.saborService.cadastrar(saborDTO));
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> editar(@RequestParam("id") final Long id, @RequestBody final SaborDTO saborDTO){
        try{
            return ResponseEntity.ok( this.saborService.editar(saborDTO, id));
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity <List<SaborDTO>> findAll(){
        try{
            return ResponseEntity.ok(this.saborService.findAll());
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<SaborDTO> findById(@RequestParam final Long id){
    try{
        return ResponseEntity.ok(this.saborService.findById(id));
    }
    catch (Exception e){
        throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
    }

    @DeleteMapping
    public ResponseEntity<String> deletar(@RequestParam("id") final Long id) {
        try {
            return ResponseEntity.ok(this.saborService.deletar(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
