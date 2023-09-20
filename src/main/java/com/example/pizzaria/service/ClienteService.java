package com.example.pizzaria.service;

import com.example.pizzaria.dto.ClienteDTO;
import com.example.pizzaria.dto.EnderecoDTO;
import com.example.pizzaria.entity.Cliente;
import com.example.pizzaria.entity.Endereco;
import com.example.pizzaria.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;

     static final String NOTFOUND = "Registro não encontrado";
    static final String CPFNOTFOUND = "CPF não encontrado";
    static final String CPFDUPLICATED = "CPF já cadastrado";
    static final String SUCESS = "Operação realizada com sucesso";
    static final String FAILLIST = "Lista não encontrada";


    public List<ClienteDTO> findAll()    {
        List<Cliente> clientes = this.clienteRepository.findAll();
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        if(clientes.isEmpty()){
            throw new IllegalArgumentException(FAILLIST);
        }else{
            for (Cliente i : clientes
            ) {
                clientesDTO.add(modelMapper.map(i, ClienteDTO.class));
            }
            return clientesDTO;
        }
    }

    public ClienteDTO findById(Long id)
    {
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow(()-> new RuntimeException(NOTFOUND));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    public void cadastrar(ClienteDTO clienteDTO)
    {
        Assert.isTrue(!(this.clienteRepository.alreadyExists(clienteDTO.getCpf())), CPFNOTFOUND);
        this.clienteRepository.save(modelMapper.map(clienteDTO, Cliente.class));
    }

    public String editar(ClienteDTO clienteDTO, Long id)
    {
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow(()-> new RuntimeException(NOTFOUND));
        if(this.clienteRepository.alreadyExists(clienteDTO.getCpf()))
        {
           return CPFDUPLICATED;
        }
        modelMapper.map(clienteDTO,cliente);
        this.clienteRepository.save(cliente);
        return SUCESS;

    }

    public boolean deletar(Long id)
    {
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow(()-> new RuntimeException(NOTFOUND));
        cliente.setAtivo(false);
        this.clienteRepository.save(cliente);
        return true;
    }

}
