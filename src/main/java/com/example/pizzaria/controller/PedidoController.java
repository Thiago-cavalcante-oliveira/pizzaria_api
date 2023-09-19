package com.example.pizzaria.controller;

import com.example.pizzaria.dto.PedidoDTO;
import com.example.pizzaria.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "api/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("all")
    public ResponseEntity<List<PedidoDTO>> findAll()
    {
        try{
            return ResponseEntity.ok(this.pedidoService.findAll());
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<PedidoDTO> findById(@RequestParam("id") final Long id){

        try{
            return ResponseEntity.ok(this.pedidoService.findById(id));
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody final PedidoDTO pedidoDTO){

        try{
            this.pedidoService.cadastrar(pedidoDTO);

            return ResponseEntity.ok("Pedido cadastrado com sucesso");
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> editar(
            @RequestParam("id") final Long id,
            @RequestBody final PedidoDTO pedidoDTO)
    {
        try {
            this.pedidoService.editar(pedidoDTO, id);
            return ResponseEntity.ok("Pedido alterado com sucesso");
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
            this.pedidoService.deletar(id);
            return ResponseEntity.ok("Pedido desativado com sucesso");
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
