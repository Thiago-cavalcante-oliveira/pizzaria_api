package com.example.pizzaria.service;

import com.example.pizzaria.dto.ProdutoDiversoDTO;
import com.example.pizzaria.entity.ProdutoDiverso;
import com.example.pizzaria.repository.ProdutoDiversoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoDiversoService {

    @Autowired
    private ProdutoDiversoRepositorio produtoDiversoRepositorio;

    public void cadastrar(ProdutoDiversoDTO produtoDiversoDTO) {
        Assert.notNull(produtoDiversoDTO.getTipo(), "Tipo nao pode ser Nulo");
        Assert.notNull(produtoDiversoDTO.getNome(), "Nome nao pode ser Nulo");
        Assert.notNull(produtoDiversoDTO.getPreco(), "Preco nao pode ser Nulo");
        Assert.notNull(produtoDiversoDTO.getQuantidade(), "Quantidade nao pode ser Nulo");

        ProdutoDiverso produtoDiverso = convertToEntity(produtoDiversoDTO);
        this.produtoDiversoRepositorio.save(produtoDiverso);

    }

    public void editar(ProdutoDiversoDTO produtoDiversoDTO, Long id) {
        Assert.notNull(produtoDiversoDTO.getTipo(), "Tipo nao pode ser Nulo");
        Assert.notNull(produtoDiversoDTO.getNome(), "Nome nao pode ser Nulo");
        Assert.notNull(produtoDiversoDTO.getPreco(), "Preco nao pode ser Nulo");
        Assert.notNull(produtoDiversoDTO.getQuantidade(), "Quantidade nao pode ser Nulo");

        ProdutoDiverso produtoDiverso = convertToEntity(produtoDiversoDTO);
        this.produtoDiversoRepositorio.save(produtoDiverso);
    }

    public ProdutoDiversoDTO findById(Long id) {
        ProdutoDiverso produtoDiverso = this.produtoDiversoRepositorio.findById(id).orElse(null);
        return produtoDiverso == null
                ? null
                : convertToDTO(produtoDiverso);
    }




    private ProdutoDiverso convertToEntity(ProdutoDiversoDTO produtoDiversoDTO) {
        ProdutoDiverso produtoDiverso = new ProdutoDiverso();
        produtoDiverso.setTipo(produtoDiversoDTO.getTipo());
        produtoDiverso.setNome(produtoDiversoDTO.getNome());
        produtoDiverso.setPreco(produtoDiversoDTO.getPreco());
        produtoDiverso.setQuantidade(produtoDiversoDTO.getQuantidade());
        return produtoDiverso;
    }

    private ProdutoDiversoDTO convertToDTO(ProdutoDiverso produtoDiverso) {
        ProdutoDiversoDTO produtoDiversoDTO = new ProdutoDiversoDTO();
        produtoDiversoDTO.setTipo(produtoDiverso.getTipo());
        produtoDiversoDTO.setNome(produtoDiverso.getNome());
        produtoDiversoDTO.setPreco(produtoDiverso.getPreco());
        produtoDiversoDTO.setQuantidade(produtoDiverso.getQuantidade());
        produtoDiversoDTO.setId(produtoDiverso.getId());
        return produtoDiversoDTO;
    }


   public List<ProdutoDiversoDTO> findAll() {
List<ProdutoDiverso> produtoDiversos = this.produtoDiversoRepositorio.findAll();
List<ProdutoDiversoDTO> produtoDiversosDTO = new ArrayList<>();
       for (ProdutoDiverso i: produtoDiversos) {

           produtoDiversosDTO.add(convertToDTO(i));
       }

         return produtoDiversosDTO;



    }
}
