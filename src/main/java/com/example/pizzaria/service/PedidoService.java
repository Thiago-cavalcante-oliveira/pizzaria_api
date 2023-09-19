package com.example.pizzaria.service;


import com.example.pizzaria.dto.ClienteDTO;
import com.example.pizzaria.dto.EnderecoDTO;
import com.example.pizzaria.dto.FuncionarioDTO;
import com.example.pizzaria.dto.PedidoDTO;
import com.example.pizzaria.entity.*;
import com.example.pizzaria.dto.ProdutoDiversoDTO;
import com.example.pizzaria.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PedidoService {


    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ModelMapper modelMapper;
    static String fail = "Registro nao encontrado.";
    public List<PedidoDTO> findAll(){
        List<Pedido> pedidos = this.pedidoRepository.findAll();
        List<PedidoDTO> pedidosDTO = new ArrayList<>();
        for(Pedido i : pedidos)
        {
            pedidosDTO.add(modelMapper.map(i, PedidoDTO.class));
        }
        return pedidosDTO;
    }

    public PedidoDTO findById(Long id)
    {
        Pedido pedido = this.pedidoRepository.findById(id).orElseThrow(()-> new RuntimeException(fail));
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public void cadastrar(PedidoDTO pedidoDTO)
    {
        this.pedidoRepository.save(modelMapper.map(pedidoDTO, Pedido.class));
    }

    public void editar(PedidoDTO pedidoDTO, Long id)
    {
        Pedido pedido = this.pedidoRepository.findById(id).orElseThrow(()-> new RuntimeException(fail));
        modelMapper.map(pedidoDTO, pedido);
        this.pedidoRepository.save(pedido);
    }

    public void deletar(Long id)
    {
        Pedido pedido = this.pedidoRepository.findById(id).orElseThrow(()-> new RuntimeException(fail));
        pedido.setAtivo(false);
        this.pedidoRepository.save(pedido);
    }

}
