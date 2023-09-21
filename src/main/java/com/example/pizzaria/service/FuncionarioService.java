package com.example.pizzaria.service;

import com.example.pizzaria.dto.FuncionarioDTO;
import com.example.pizzaria.dto.SaborDTO;
import com.example.pizzaria.entity.Funcionario;
import com.example.pizzaria.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    static final String FAIL = "Registro não encontrado";
    static final String DUPLICATED = "CPF já cadastrado";
    static final String FAILLIST = "Lista não encontrada";


    public List<FuncionarioDTO> findAll(){

        List<Funcionario> funcionarios = this.funcionarioRepository.findAll();
        List<FuncionarioDTO> funcionariosDTO = new ArrayList<>();

        if(funcionarios.isEmpty()){
            throw new IllegalArgumentException(FAILLIST);
        } else{
            for(Funcionario i : funcionarios)
        {
            funcionariosDTO.add(modelMapper.map(i, FuncionarioDTO.class));
        }
            return funcionariosDTO;
        }
    }

    public FuncionarioDTO findById(Long id)
    {
        Funcionario funcionario = this.funcionarioRepository.findById(id).orElseThrow(()-> new RuntimeException(FAIL));

        return modelMapper.map(funcionario, FuncionarioDTO.class);
    }

    public void cadastrar(FuncionarioDTO funcionarioDTO)
    {
        Assert.isTrue(!(this.funcionarioRepository.alreadyExists(funcionarioDTO.getCpf())), DUPLICATED);
        this.funcionarioRepository.save(modelMapper.map(funcionarioDTO, Funcionario.class));
    }

    public void editar(FuncionarioDTO funcionarioDTO, Long id)
    {
        Funcionario funcionario = this.funcionarioRepository.findById(id).orElseThrow(()-> new RuntimeException(FAIL));
        if(this.funcionarioRepository.alreadyExists(funcionarioDTO.getCpf()))
        {
            Assert.isTrue( this.funcionarioRepository.isTheSame(funcionarioDTO.getCpf()).equals(id) , DUPLICATED);
        }
        modelMapper.map(funcionarioDTO,funcionario);
        this.funcionarioRepository.save(funcionario);
    }

    public void deletar(Long id)
    {
        Funcionario funcionario = this.funcionarioRepository.findById(id).orElseThrow(()-> new RuntimeException(FAIL));
        funcionario.setAtivo(false);
        this.funcionarioRepository.save(funcionario);
    }

}
