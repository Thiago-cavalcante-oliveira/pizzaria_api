package com.example.pizzaria.controller;

import com.example.pizzaria.dto.PedidoDTO;
import com.example.pizzaria.dto.Resposta;
import com.example.pizzaria.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "api/pedido")
@CrossOrigin(origins = "http://localhost:4200")
public class PedidoController {

    static final String SUCESSO = "Operacao realizada com sucesso";
    static final String DISABLED = "Item inativado com sucesso";

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

    @GetMapping("situacao")
    public ResponseEntity<List<PedidoDTO>> findBySituacao(@RequestParam("s") final String situacao)
    {
        try{
            return ResponseEntity.ok(this.pedidoService.findBySituacao(situacao));
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
    public ResponseEntity<PedidoDTO> cadastrar(@RequestBody final PedidoDTO pedidoDTO){

        try{


            return ResponseEntity.ok(this.pedidoService.cadastrar(pedidoDTO));
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<PedidoDTO> editar(
            @RequestParam("id") final Long id,
            @RequestBody final PedidoDTO pedidoDTO)
    {
        try {

            return ResponseEntity.ok(this.pedidoService.editar(pedidoDTO, id));
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
            resposta.setMensangem( this.pedidoService.deletar(id));
            return ResponseEntity.ok(resposta);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
