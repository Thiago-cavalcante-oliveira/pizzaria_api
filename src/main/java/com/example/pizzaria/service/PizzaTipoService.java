package com.example.pizzaria.service;

import com.example.pizzaria.dto.PizzaTipoDTO;
import com.example.pizzaria.entity.PizzaTipo;
import com.example.pizzaria.repository.PizzaTipoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PizzaTipoService {

    @Autowired
    private PizzaTipoRepository pizzaTipoRepository;
    @Autowired
    private ModelMapper modelMapper;

    static final String SUCCESS = "Tipo de pizza cadastrado com sucesso";
    static final String FAIL = "Tipo de pizza não cadastrado";
    static final String EDITED = "Tipo de pizza editado com sucesso";
    static final String DELETED = "Tipo de pizza deletado com sucesso";
    static final String DISABLED = "Tipo de pizza desativado com sucesso";
    static final String DUPLICATED = "Tipo de pizza já cadastrado";


    public String cadastrar(PizzaTipoDTO pizzaTipoDTO) {
        if (pizzaTipoRepository.existsByNome(pizzaTipoDTO.getNome())) {
            throw new IllegalArgumentException(DUPLICATED);
        }
        this.pizzaTipoRepository.save(modelMapper.map(pizzaTipoDTO, PizzaTipo.class));
        return SUCCESS;
    }

    public String editar(PizzaTipoDTO pizzaTipoDTO, Long id) {
        if (pizzaTipoDTO.getId() != id) {
            throw new IllegalArgumentException(FAIL);
        }
        else{
            this.pizzaTipoRepository.save(modelMapper.map(pizzaTipoDTO, PizzaTipo.class));
            return EDITED;
        }
    }



    public PizzaTipoDTO findById(Long id) {
        return modelMapper.map(this.pizzaTipoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(FAIL)), PizzaTipoDTO.class);
    }

    public List<PizzaTipoDTO> findAll() {
        List<PizzaTipo> pizzaTipos = this.pizzaTipoRepository.findAll();
        List<PizzaTipoDTO> pizzaTipoDTO = new ArrayList<>();
        for (PizzaTipo i : pizzaTipos) {
            pizzaTipoDTO.add(modelMapper.map(i, PizzaTipoDTO.class));
        }
        if(pizzaTipoDTO.isEmpty()){
            throw new IllegalArgumentException(FAIL);
        }
        return pizzaTipoDTO;
    }

    public String deletar(Long id) {
        if (!pizzaTipoRepository.existsById(id)) {
            throw new IllegalArgumentException(FAIL);
        } else if (pizzaTipoRepository.pizzaTipoExistTb_pizza(id)) {
            PizzaTipo salvarEmBanco = this.pizzaTipoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(FAIL));
            salvarEmBanco.setAtivo(false);
            this.pizzaTipoRepository.save(salvarEmBanco);
            return DISABLED;
        } else {
            this.pizzaTipoRepository.deleteById(id);
            return DELETED;
        }
    }


}
