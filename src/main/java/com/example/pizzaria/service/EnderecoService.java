package com.example.pizzaria.service;

import com.example.pizzaria.dto.EnderecoDTO;
import com.example.pizzaria.entity.Endereco;
import com.example.pizzaria.entity.Funcionario;
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

    static final String DELETED = "Endereco deletado com sucesso";
    static final String DISABLED = "Endereco desativado com sucesso";


    public List<EnderecoDTO> findAll() {
        List<Endereco> enderecos = this.enderecoRepository.findAll();
        List<EnderecoDTO> enderecosDTO = new ArrayList<>();

        if(enderecos.isEmpty()){
            throw new IllegalArgumentException(FAILLIST);
        }else{
            for (Endereco i : enderecos
            ) {
                enderecosDTO.add(modelMapper.map(i, EnderecoDTO.class));
            }
            return enderecosDTO;
        }
    }

    public EnderecoDTO findById(Long id) {
        Endereco endereco = this.enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException(FAIL));

        return modelMapper.map(endereco, EnderecoDTO.class);
    }

    public EnderecoDTO cadastrar(EnderecoDTO enderecoDTO) {

        Endereco enderecoSalvo = this.enderecoRepository.save(modelMapper.map(enderecoDTO, Endereco.class));
        return modelMapper.map(enderecoSalvo, EnderecoDTO.class);
    }

    public EnderecoDTO editar(EnderecoDTO enderecoDTO, Long id) {
        Endereco endereco = this.enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException(FAIL));

        modelMapper.map(enderecoDTO, endereco);
        Endereco enderecoSalvo =  this.enderecoRepository.save(endereco);
        return modelMapper.map(enderecoSalvo, EnderecoDTO.class);
    }

    public String deletar(Long id) {
        if (!enderecoRepository.doesExist(id)) {
            throw new IllegalArgumentException(FAIL);
        } else if (true /*funcionarioRepository.(id)*/) {
            Endereco endereco = this.enderecoRepository.findById(id).orElseThrow(()-> new RuntimeException(FAIL));
            endereco.setAtivo(false);
            this.enderecoRepository.save(endereco);
            return DISABLED;
        } else {
            this.enderecoRepository.deleteById(id);
            return DELETED;
        }
    }


}
