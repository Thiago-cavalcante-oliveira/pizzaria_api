package com.example.pizzaria.service;

import com.example.pizzaria.dto.FuncionarioDTO;
import com.example.pizzaria.entity.Funcionario;
import com.example.pizzaria.entity.PizzaTipo;
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

    static final String DELETED = "Funcionario deletado com sucesso";
    static final String DISABLED = "Funcionario desativado com sucesso";


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

    public FuncionarioDTO cadastrar(FuncionarioDTO funcionarioDTO)
    {
        Assert.isTrue(!(this.funcionarioRepository.alreadyExists(funcionarioDTO.getCpf())), DUPLICATED);
        Funcionario funcionarioSalvo = this.funcionarioRepository.save(modelMapper.map(funcionarioDTO, Funcionario.class));
        return modelMapper.map(funcionarioSalvo, FuncionarioDTO.class);
    }

    public FuncionarioDTO editar(FuncionarioDTO funcionarioDTO, Long id)
    {
        if(this.funcionarioRepository.alreadyExists(funcionarioDTO.getCpf()))
        {
            Assert.isTrue( this.funcionarioRepository.isTheSame(funcionarioDTO.getCpf()).equals(id) , DUPLICATED);
        }
        Funcionario funcionario = this.funcionarioRepository.findById(id).orElseThrow(()-> new RuntimeException(FAIL));

        modelMapper.map(funcionarioDTO,funcionario);
        Funcionario funcionarioSalvo = this.funcionarioRepository.save(funcionario);
        return modelMapper.map(funcionarioSalvo, FuncionarioDTO.class);
    }

    public String deletar(Long id)
    {



        if (!funcionarioRepository.doesExist(id)) {
            throw new IllegalArgumentException(FAIL);
        } else if (this.funcionarioRepository.existsInAtendentePedido(id) || this.funcionarioRepository.existsInEntregadorPedido(id)) {
                Funcionario funcionario = this.funcionarioRepository.findById(id).orElseThrow(()-> new RuntimeException(FAIL));
                funcionario.setAtivo(false);
                this.funcionarioRepository.save(funcionario);
            return DISABLED;
        } else {
            this.funcionarioRepository.deleteById(id);
            return DELETED;
        }
    }

}
