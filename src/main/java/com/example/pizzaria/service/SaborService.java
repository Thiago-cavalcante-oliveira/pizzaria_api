package com.example.pizzaria.service;

import com.example.pizzaria.dto.SaborDTO;
import com.example.pizzaria.entity.Sabor;
import com.example.pizzaria.repository.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SaborService {

    @Autowired
    private SaborRepository saborRepository;
    @Autowired
    private ModelMapper modelMapper;

    static final String SUCCESS = "Sabor cadastrado com sucesso";
    static final String FAIL ="Sabor não cadastrado";
    static final String EDITED = "Sabor editado com sucesso";
    static final String DELETE = "Sabor deletado com sucesso";
    static final String DISABLED = "Sabor desativado com sucesso";
    static final String DUPLICATED = "Sabor já cadastrado";

    public String cadastrar(SaborDTO saborDTO) {
        if (this.saborRepository.existsByNome(saborDTO.getNome())) {
            throw new IllegalArgumentException(DUPLICATED);
        }

        this.saborRepository.save(modelMapper.map(saborDTO, Sabor.class));
        return SUCCESS;
    }
    public String editar(SaborDTO saborDTO, Long id) {
        Long idFront = id;
        if (saborDTO.getId() != idFront) {
            throw new IllegalArgumentException("Os IDs não coincidem");
        } else if (saborRepository.findByNome(saborDTO.getNome()).getId() != idFront) {
            throw new IllegalArgumentException(DUPLICATED);
        }
        this.saborRepository.save(modelMapper.map(saborDTO, Sabor.class));
        return EDITED;
    }
    public List<SaborDTO> findAll() {
        List<Sabor> sabores = this.saborRepository.findAll();
        if(sabores.isEmpty()){
            throw new IllegalArgumentException(FAIL);
        }else{
        List<SaborDTO> saboresDTO = new ArrayList<>();
        for (Sabor i : sabores
        ) {
            saboresDTO.add(modelMapper.map(i, SaborDTO.class));
        }
        return saboresDTO;

    }}
    public SaborDTO findById(Long id) {
                        return modelMapper.map(this.saborRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(FAIL)), SaborDTO.class);
    }
    public String deletar(Long id){
        if(saborRepository.saborExistTb_pizza(id)){
            Sabor sabor = this.saborRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(FAIL));
            sabor.setAtivo(false);
            throw new IllegalArgumentException(DISABLED);
        }
        else{
            this.saborRepository.deleteById(id);
            return DELETE;
        }
    }

}
