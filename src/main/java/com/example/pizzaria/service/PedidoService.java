package com.example.pizzaria.service;


import com.example.pizzaria.dto.PedidoDTO;
import com.example.pizzaria.entity.*;
import com.example.pizzaria.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PedidoService {


    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ModelMapper modelMapper;
    static final String FAIL = "Registro nao encontrado.";

    static final String DISABLED = "Pedido desativado com sucesso";


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

    public List<PedidoDTO> findBySituacao(String situacao){
        List<Pedido> pedidos = this.pedidoRepository.findPedidoBySituacaoPedido(situacao);
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

    public PedidoDTO cadastrar(PedidoDTO pedidoDTO)
    {

        pedidoDTO.setDataPedido(adicionarData());
        Pedido pedidoSalvo = this.pedidoRepository.save(modelMapper.map(pedidoDTO, Pedido.class));
        return modelMapper.map(pedidoSalvo, PedidoDTO.class);
    }

    public PedidoDTO editar(PedidoDTO pedidoDTO, Long id)
    {
        if (!(pedidoDTO.getId().equals(id))) {
            throw new IllegalArgumentException("Os IDs não coincidem");
        }

        Pedido pedidoSalvo = this.pedidoRepository.save(modelMapper.map(pedidoDTO, Pedido.class));
        return modelMapper.map(pedidoSalvo, PedidoDTO.class);
    }

    public String deletar(Long id)
    {
        if (!pedidoRepository.doesExist(id)) {
            throw new IllegalArgumentException(FAIL);
        }
        Pedido pedido = this.pedidoRepository.findById(id).orElseThrow(()-> new RuntimeException(FAIL));
        pedido.setAtivo(false);
        this.pedidoRepository.save(pedido);
        return DISABLED;
    }

    private String adicionarData(){

        Date data = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String strDate = format.format(data);

        return strDate;
    }

}
