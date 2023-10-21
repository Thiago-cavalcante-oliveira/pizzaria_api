package com.example.pizzaria.service;

import com.example.pizzaria.dto.PizzaDTO;
import com.example.pizzaria.entity.Pizza;
import com.example.pizzaria.entity.ProdutoDiverso;
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

    static final String SUCCESS = "Pizza cadastrada com sucesso";
    static final String FAIL = "Pizza não cadastrada";
    static final String EDITED = "Pizza editada com sucesso";
    static final String DELETED = "Pizza deletada com sucesso";

    static final String DISABLED = "Produto desativado com sucesso";






    public PizzaDTO findById(Long id) {
        return  modelMapper.map(this.pizzaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(FAIL)), PizzaDTO.class);
    }

    public List<PizzaDTO> findAll() {
        List<Pizza> pizzas = this.pizzaRepository.findAll();
        if (pizzas.isEmpty()) {
            throw new IllegalArgumentException(FAIL);
        } else {
            List<PizzaDTO> pizzasDTO = new ArrayList<>();
            for (Pizza i : pizzas
            ) {
                pizzasDTO.add(modelMapper.map(i, PizzaDTO.class));
            }
            return pizzasDTO;
        }
    }

    public PizzaDTO cadastrar(PizzaDTO pizza) {
        Pizza pizzaSalva = this.pizzaRepository.save(modelMapper.map(pizza, Pizza.class));
        return modelMapper.map(pizzaSalva, PizzaDTO.class);
    }

    public PizzaDTO editar(PizzaDTO pizza, Long id) {
        if (!Objects.equals(pizza.getId(), id)) {
            throw new IllegalArgumentException("Os IDs não coincidem");
        }
        else {

            Pizza pizzaSalva = this.pizzaRepository.save(modelMapper.map(pizza, Pizza.class));
            return modelMapper.map(pizzaSalva, PizzaDTO.class);
        }
    }

    public String deletar(Long id) {
        if(true/*saborRepository.saborExistTb_pizza(id)*/){
            Pizza pizza = this.pizzaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(FAIL));
            pizza.setAtivo(false);
            this.pizzaRepository.save(pizza);
            return DISABLED;
        }
        else{
            this.pizzaRepository.deleteById(id);
            return DELETED;
        }


    }
}
