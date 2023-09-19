package com.example.pizzaria.service;

import com.example.pizzaria.config.ModelMapperConfig;
import com.example.pizzaria.dto.PizzaDTO;
import com.example.pizzaria.dto.PizzaTipoDTO;
import com.example.pizzaria.dto.SaborDTO;
import com.example.pizzaria.entity.Pizza;
import com.example.pizzaria.entity.PizzaTipo;
import com.example.pizzaria.entity.Sabor;
import com.example.pizzaria.repository.PizzaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;
    @Autowired
    private ModelMapper modelMapper;


    public PizzaDTO convertToDTO(Pizza pizza) {
        PizzaDTO pizzaDTO = new PizzaDTO();
        List<SaborDTO> sabores = new ArrayList<>();
        for (Sabor i : pizza.getSabor()
        ) {
            sabores.add(modelMapper.map(i, SaborDTO.class));
        }
        pizzaDTO.setSaborDTO(sabores);
        pizzaDTO.setTipoDTO(modelMapper.map(pizza.getTipo(), PizzaTipoDTO.class));
        return pizzaDTO;
    }


    public PizzaDTO findById(Long id) {
        PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO = modelMapper.map(this.pizzaRepository.findById(id).orElse(null), PizzaDTO.class);
        if (pizzaDTO == null) {
            throw new RuntimeException("Registro não encontrado");
        }
        return pizzaDTO;
    }

    public List<PizzaDTO> findAll() {
        List<Pizza> pizzas = this.pizzaRepository.findAll();
        if (pizzas.isEmpty()) {
            throw new RuntimeException("Não há pizzas cadastradas");
        } else {
            List<PizzaDTO> pizzasDTO = new ArrayList<>();
            for (Pizza i : pizzas
            ) {
                pizzasDTO.add(modelMapper.map(i, PizzaDTO.class));
            }
            return pizzasDTO;
        }
    }

    public String cadastrar(PizzaDTO pizza) {
        Pizza salvarEmBanco = modelMapper.map(pizza, Pizza.class);
        this.pizzaRepository.save(salvarEmBanco);
        return "Pizza cadastrada com sucesso";
    }

    public String editar(PizzaDTO pizza, Long id) {
        if (!Objects.equals(pizza.getId(), id)) {
            throw new RuntimeException("Os IDs não coincidem");
        } else if (!pizzaRepository.existsById(id)) {
            throw new RuntimeException("Pizza não encontrada");
        } else {

            this.pizzaRepository.save(modelMapper.map(pizza, Pizza.class));
            return "Pizza editada com sucesso";
        }
    }

    public String deletar(Long id) {
        if (!pizzaRepository.existsById(id)) {
            throw new RuntimeException("Pizza não encontrada");
        } else {
            this.pizzaRepository.deleteById(id);
            return "Pizza deletada com sucesso";
        }


    }
}
