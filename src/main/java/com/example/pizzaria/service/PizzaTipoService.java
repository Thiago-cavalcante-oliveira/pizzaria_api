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

    static final String success = "Tipo de pizza cadastrado com sucesso";
    static final String fail = "Tipo de pizza não cadastrado";
    static final String edited = "Tipo de pizza editado com sucesso";
    static final String deleted = "Tipo de pizza deletado com sucesso";
    static final String disabled = "Tipo de pizza desativado com sucesso";
    static final String duplicated = "Tipo de pizza já cadastrado";


    public String cadastrar(PizzaTipoDTO pizzaTipoDTO) {
        if (pizzaTipoRepository.existsByNome(pizzaTipoDTO.getNome())) {
            throw new RuntimeException(duplicated);
        }
        this.pizzaTipoRepository.save(modelMapper.map(pizzaTipoDTO, PizzaTipo.class));
        return success;
    }

    public String editar(PizzaTipoDTO pizzaTipoDTO, Long id) {
        if (pizzaTipoDTO.getId() != id) {
            throw new RuntimeException(fail);
        } else if (pizzaTipoRepository.findByNome(pizzaTipoDTO.getNome()).getId() != id) {
            throw new RuntimeException(duplicated);
        }
        this.pizzaTipoRepository.save(modelMapper.map(pizzaTipoDTO, PizzaTipo.class));
        return edited;
    }

    public PizzaTipoDTO findById(Long id) {
        PizzaTipo pizzaTipo = this.pizzaTipoRepository.findById(id).orElse(null);
        return pizzaTipo == null
                ? null
                : modelMapper.map(pizzaTipo, PizzaTipoDTO.class);
    }

    public List<PizzaTipoDTO> findAll() {
        List<PizzaTipo> pizzaTipos = this.pizzaTipoRepository.findAll();
        List<PizzaTipoDTO> pizzaTipoDTO = new ArrayList<>();
        for (PizzaTipo i : pizzaTipos) {

            pizzaTipoDTO.add(modelMapper.map(i, PizzaTipoDTO.class));
        }
        return pizzaTipoDTO;
    }

    public String deletar(Long id) {
        if (!pizzaTipoRepository.existsById(id)) {
            throw new RuntimeException(duplicated);
        } else if (pizzaTipoRepository.pizzaTipoExistTb_pizza(id)) {
            PizzaTipo salvarEmBanco = this.pizzaTipoRepository.findById(id).orElse(null);
            salvarEmBanco.setAtivo(false);
            this.pizzaTipoRepository.save(salvarEmBanco);
            return disabled;
        } else {
            this.pizzaTipoRepository.deleteById(id);
            return deleted;
        }
    }


}
