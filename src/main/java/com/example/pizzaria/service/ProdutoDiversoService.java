package com.example.pizzaria.service;

import com.example.pizzaria.dto.ProdutoDiversoDTO;
import com.example.pizzaria.entity.ProdutoDiverso;
import com.example.pizzaria.repository.ProdutoDiversoRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoDiversoService {

    static final String FAIL ="Produto não cadastrado";
    @Autowired
    private ProdutoDiversoRepositorio produtoDiversoRepositorio;

    @Autowired
    private ModelMapper modelMapper;


    public void cadastrar(ProdutoDiversoDTO produtoDiversoDTO) {
        this.produtoDiversoRepositorio.save(modelMapper.map(produtoDiversoDTO, ProdutoDiverso.class));
    }

    public void editar(ProdutoDiversoDTO produtoDiversoDTO) {
               this.produtoDiversoRepositorio.save(modelMapper.map(produtoDiversoDTO, ProdutoDiverso.class));
    }
    public ProdutoDiversoDTO findById(Long id) {
        ProdutoDiverso produtoDiverso = this.produtoDiversoRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException(FAIL));
        return modelMapper.map(produtoDiverso, ProdutoDiversoDTO.class);
    }
   public List<ProdutoDiversoDTO> findAll() {
List<ProdutoDiverso> produtoDiversos = this.produtoDiversoRepositorio.findAll();
List<ProdutoDiversoDTO> produtoDiversosDTO = new ArrayList<>();
       for (ProdutoDiverso i: produtoDiversos) {
           produtoDiversosDTO.add(modelMapper.map((i), ProdutoDiversoDTO.class));
       }
         return produtoDiversosDTO;
    }

    public void deletar(Long id){
        ProdutoDiverso produtoDiverso = this.produtoDiversoRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException(FAIL));
        produtoDiverso.setAtivo(false);
        this.produtoDiversoRepositorio.save(produtoDiverso);
    }
}
