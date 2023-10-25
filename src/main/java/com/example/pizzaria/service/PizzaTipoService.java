package com.example.pizzaria.service;

import com.example.pizzaria.dto.PizzaTipoDTO;
import com.example.pizzaria.dto.SaborDTO;
import com.example.pizzaria.entity.PizzaTipo;
import com.example.pizzaria.entity.Sabor;
import com.example.pizzaria.repository.PizzaTipoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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


    public PizzaTipoDTO cadastrar(PizzaTipoDTO pizzaTipoDTO) {
        if (pizzaTipoRepository.existsByNome(pizzaTipoDTO.getNome())) {
            throw new IllegalArgumentException(DUPLICATED);
        }
        PizzaTipo pizzaTipoSalvo = this.pizzaTipoRepository.save(modelMapper.map(pizzaTipoDTO, PizzaTipo.class));
        return modelMapper.map(pizzaTipoSalvo, PizzaTipoDTO.class);
    }

    public PizzaTipoDTO editar(PizzaTipoDTO pizzaTipoDTO, Long id) {
        Long idFront = id;
        if (!Objects.equals(pizzaTipoDTO.getId(), idFront)) {

            throw new IllegalArgumentException("Os IDs não coincidem");
        } else
        if(this.pizzaTipoRepository.alreadyExists(pizzaTipoDTO.getNome())){
            if (!Objects.equals(pizzaTipoRepository.findByNome(pizzaTipoDTO.getNome()).getId(), idFront)) {
                throw new IllegalArgumentException(DUPLICATED);
            }
        }

        PizzaTipo pizzaTipoSalvo = this.pizzaTipoRepository.save(modelMapper.map(pizzaTipoDTO, PizzaTipo.class));
        return modelMapper.map(pizzaTipoSalvo, PizzaTipoDTO.class);
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

    public List<PizzaTipoDTO> findAllAtivo() {
        List<PizzaTipo> pizzaTipos = this.pizzaTipoRepository.findAllByAtivoIsTrue();
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
