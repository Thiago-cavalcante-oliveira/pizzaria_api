package com.example.pizzaria.service;

import com.example.pizzaria.dto.EnderecoDTO;
import com.example.pizzaria.dto.SaborDTO;
import com.example.pizzaria.entity.Endereco;
import com.example.pizzaria.entity.Sabor;
import com.example.pizzaria.repository.EnderecoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ModelMapper modelMapper;

    static final String FAIL = "Registro não encontrado";
    static final String FAILLIST = "Lista não encontrada";


    public List<EnderecoDTO> findAll() {
        List<Endereco> enderecos = this.enderecoRepository.findAll();
        List<EnderecoDTO> enderecosDTO = new ArrayList<>();

        if(enderecos.isEmpty()){
            throw new IllegalArgumentException(FAILLIST);
        }else{
            List<EnderecoDTO> enderecoDTOS = new ArrayList<>();
            for (Endereco i : enderecos
            ) {
                enderecoDTOS.add(modelMapper.map(i, EnderecoDTO.class));
            }
            return enderecosDTO;
        }
    }

    public EnderecoDTO findById(Long id) {
        Endereco endereco = this.enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException(FAIL));

        return modelMapper.map(endereco, EnderecoDTO.class);
    }

    public void cadastrar(EnderecoDTO enderecoDTO) {

        this.enderecoRepository.save(modelMapper.map(enderecoDTO, Endereco.class));
    }

    public void editar(EnderecoDTO enderecoDTO, Long id) {
        Endereco endereco = this.enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException(FAIL));

        modelMapper.map(enderecoDTO, endereco);
        this.enderecoRepository.save(endereco);
    }

    public boolean deletar(Long id) {
        Endereco endereco = this.enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException(FAIL));
        endereco.setAtivo(false);
        this.enderecoRepository.save(endereco);
        return true;
    }


}
