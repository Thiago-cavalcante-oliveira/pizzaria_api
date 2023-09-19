package com.example.pizzaria.service;

import com.example.pizzaria.dto.PizzaTipoDTO;
import com.example.pizzaria.dto.ProdutoDiversoDTO;
import com.example.pizzaria.entity.PizzaTipo;
import com.example.pizzaria.entity.ProdutoDiverso;
import com.example.pizzaria.repository.PizzaTipoRepository;
import com.example.pizzaria.repository.ProdutoDiversoRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class PizzaTipoService {

    @Autowired
    private PizzaTipoRepository pizzaTipoRepository;
    @Autowired
    private ModelMapper modelMapper;

    public String cadastrar(PizzaTipoDTO pizzaTipoDTO) {
        if (pizzaTipoDTO.getNome() == null || pizzaTipoDTO.getNome().isEmpty()) {
            throw new RuntimeException("Nome não pode ser nulo ou vazio");
        } else if (pizzaTipoRepository.existsByNome(pizzaTipoDTO.getNome())) {
            throw new RuntimeException("Tipo já cadastrado");
        }
        this.pizzaTipoRepository.save(modelMapper.map(pizzaTipoDTO, PizzaTipo.class));
        return "Tipo de pizza cadastrado com sucesso";
    }

    public String editar(PizzaTipoDTO pizzaTipoDTO, Long id) {
        if (pizzaTipoDTO.getId() != id) {
            throw new RuntimeException("Id não corresponde ao objeto");
        } else if (pizzaTipoRepository.findByNome(pizzaTipoDTO.getNome()).getId() != id) {
            throw new RuntimeException("Tipo já cadastrado");
        }
        this.pizzaTipoRepository.save(modelMapper.map(pizzaTipoDTO, PizzaTipo.class));
        return "Tipo de pizza editado com sucesso";
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
            throw new RuntimeException("Tipo não encontrado");
        } else if (pizzaTipoRepository.pizzaTipoExistTb_pizza(id)) {
            PizzaTipo salvarEmBanco = this.pizzaTipoRepository.findById(id).orElse(null);
            salvarEmBanco.setAtivo(false);
            this.pizzaTipoRepository.save(salvarEmBanco);
            return "Tipo inativado com sucesso";
        } else {
            this.pizzaTipoRepository.deleteById(id);
            return "Tipo deletado com sucesso";
        }
    }


}
