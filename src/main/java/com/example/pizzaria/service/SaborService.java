package com.example.pizzaria.service;

import com.example.pizzaria.dto.ProdutoDiversoDTO;
import com.example.pizzaria.dto.SaborDTO;
import com.example.pizzaria.entity.ProdutoDiverso;
import com.example.pizzaria.entity.Sabor;
import com.example.pizzaria.repository.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SaborService {

    @Autowired
    private SaborRepository saborRepository;
    @Autowired
    private ModelMapper modelMapper;

    static String success = "Sabor cadastrado com sucesso",
            fail="Sabor não cadastrado",
    edited = "Sabor editado com sucesso",
            delete = "Sabor deletado com sucesso",
            disable = "Sabor desativado com sucesso",
    duplicated = "Sabor já cadastrado";

    public String cadastrar(SaborDTO saborDTO) {
        if (this.saborRepository.existsByNome(saborDTO.getNome())) {
            throw new RuntimeException(duplicated);
        }

        this.saborRepository.save(modelMapper.map(saborDTO, Sabor.class));
        return success;
    }
    public String editar(SaborDTO saborDTO, Long id) {
        Long idFront = id;
        if (saborDTO.getId() != idFront) {
            throw new RuntimeException("Os IDs não coincidem");
        } else if (saborRepository.findByNome(saborDTO.getNome()).getId() != idFront) {
            throw new RuntimeException(duplicated);
        }
        this.saborRepository.save(modelMapper.map(saborDTO, Sabor.class));
        return edited;
    }
    public List<SaborDTO> findAll() {
        List<Sabor> sabores = this.saborRepository.findAll();
        if(sabores.isEmpty()){
            throw new RuntimeException("Não há sabores cadastrados");
        }else{
        List<SaborDTO> saboresDTO = new ArrayList<>();
        for (Sabor i : sabores
        ) {
            saboresDTO.add(modelMapper.map(i, SaborDTO.class));
        }
        return saboresDTO;

    }}
    public SaborDTO findById(Long id) {
        Sabor sabor = this.saborRepository.findById(id).orElse(null);
        return sabor == null
                ? null
                : modelMapper.map(sabor, SaborDTO.class);
    }
    public String deletar(Long id){
        if(saborRepository.saborExistTb_pizza(id)){
            Optional<Sabor> sabor = this.saborRepository.findById(id);
            sabor.get().setAtivo(false);
            throw new RuntimeException(disable);
        }
        else{
            this.saborRepository.deleteById(id);
            return delete;
        }
    }

}
