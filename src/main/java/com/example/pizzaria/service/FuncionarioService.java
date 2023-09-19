package com.example.pizzaria.service;

import com.example.pizzaria.dto.FuncionarioDTO;
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

    public List<FuncionarioDTO> findAll(){
        List<Funcionario> funcionarios = this.funcionarioRepository.findAll();
        List<FuncionarioDTO> funcionariosDTO = new ArrayList<>();

        for(Funcionario i : funcionarios)
        {
            funcionariosDTO.add(modelMapper.map(i, FuncionarioDTO.class));
        }

        return funcionariosDTO;
    }

    public FuncionarioDTO findById(Long id)
    {
        Funcionario funcionario = this.funcionarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Registro não encontrado"));

        return modelMapper.map(funcionario, FuncionarioDTO.class);
    }

    public void cadastrar(FuncionarioDTO funcionarioDTO)
    {
        Assert.isTrue(!(this.funcionarioRepository.alreadyExists(funcionarioDTO.getCpf())), "CPF já cadastrado");


        this.funcionarioRepository.save(modelMapper.map(funcionarioDTO, Funcionario.class));
    }

    public void editar(FuncionarioDTO funcionarioDTO, Long id)
    {
        Funcionario funcionario = this.funcionarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Registro não encontrado"));
        if(this.funcionarioRepository.alreadyExists(funcionarioDTO.getCpf()))
        {
            Assert.isTrue( this.funcionarioRepository.isTheSame(funcionarioDTO.getCpf()).equals(id) ,"Ja existe");
        }

        modelMapper.map(funcionarioDTO,funcionario);
        this.funcionarioRepository.save(funcionario);
    }

    public boolean deletar(Long id)
    {
        Funcionario funcionario = this.funcionarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Registro não encontrado"));
        funcionario.setAtivo(false);
        this.funcionarioRepository.save(funcionario);
        return true;
    }

}
