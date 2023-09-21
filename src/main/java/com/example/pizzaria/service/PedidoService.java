package com.example.pizzaria.service;


import com.example.pizzaria.dto.PedidoDTO;
import com.example.pizzaria.entity.*;
import com.example.pizzaria.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {


    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ModelMapper modelMapper;
    static final String FAIL = "Registro nao encontrado.";

    public List<PedidoDTO> findAll(){
        List<Pedido> pedidos = this.pedidoRepository.findAll();
        if(pedidos.isEmpty()){
            throw new IllegalArgumentException(FAIL);
        }
        List<PedidoDTO> pedidosDTO = new ArrayList<>();
        for(Pedido i : pedidos)
        {
            pedidosDTO.add(modelMapper.map(i, PedidoDTO.class));
        }
        return pedidosDTO;
    }

    public PedidoDTO findById(Long id)
    {
        Pedido pedido = this.pedidoRepository.findById(id).orElseThrow(()-> new RuntimeException(FAIL));
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public void cadastrar(PedidoDTO pedidoDTO)
    {
        this.pedidoRepository.save(modelMapper.map(pedidoDTO, Pedido.class));
    }

    public void editar(PedidoDTO pedidoDTO, Long id)
    {
        if (!(pedidoDTO.getId().equals(id))) {
            throw new IllegalArgumentException("Os IDs não coincidem");
        }
        Pedido pedido = this.pedidoRepository.findById(id).orElseThrow(()-> new RuntimeException(FAIL));
        modelMapper.map(pedidoDTO, pedido);
        this.pedidoRepository.save(pedido);
    }

    public void deletar(Long id)
    {
        Pedido pedido = this.pedidoRepository.findById(id).orElseThrow(()-> new RuntimeException(FAIL));
        pedido.setAtivo(false);
        this.pedidoRepository.save(pedido);
    }

}
