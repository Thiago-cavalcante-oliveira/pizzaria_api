package com.example.pizzaria.service;

import com.example.pizzaria.dto.ProdutoDiversoDTO;
import com.example.pizzaria.entity.ProdutoDiverso;
import com.example.pizzaria.repository.ProdutoDiversoRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoDiversoService {

    static final String FAIL ="Produto não cadastrado";

    static final String TIPODUPLICATED = "Tipo já cadastrado";

    @Autowired
    private ProdutoDiversoRepositorio produtoDiversoRepositorio;

    @Autowired
    private ModelMapper modelMapper;


    public void cadastrar(ProdutoDiversoDTO produtoDiversoDTO) {
        Assert.isTrue(!(this.produtoDiversoRepositorio.alreadyExists(produtoDiversoDTO.getTipo())), TIPODUPLICATED);
        this.produtoDiversoRepositorio.save(modelMapper.map(produtoDiversoDTO, ProdutoDiverso.class));
    }

    public void editar(ProdutoDiversoDTO produtoDiversoDTO, Long id) {

        if (!(produtoDiversoDTO.getId().equals(id))) {
            throw new IllegalArgumentException("Os IDs não coincidem");
        }
        if(this.produtoDiversoRepositorio.alreadyExists(produtoDiversoDTO.getTipo()))
        {
            Assert.isTrue( this.produtoDiversoRepositorio.isTheSame(produtoDiversoDTO.getTipo()).equals(id) , TIPODUPLICATED);
        }

        ProdutoDiverso produtoDiverso = this.produtoDiversoRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException(FAIL));


        modelMapper.map(produtoDiversoDTO,produtoDiverso);
        this.produtoDiversoRepositorio.save(produtoDiverso);
    }
    public ProdutoDiversoDTO findById(Long id) {
        ProdutoDiverso produtoDiverso = this.produtoDiversoRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException(FAIL));
        return modelMapper.map(produtoDiverso, ProdutoDiversoDTO.class);
    }
   public List<ProdutoDiversoDTO> findAll() {
List<ProdutoDiverso> produtoDiversos = this.produtoDiversoRepositorio.findAll();
       if(produtoDiversos.isEmpty()){
           throw new IllegalArgumentException(FAIL);
       }
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
