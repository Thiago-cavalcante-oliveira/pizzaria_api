package com.example.pizzaria.service;

import com.example.pizzaria.dto.ClienteDTO;
import com.example.pizzaria.entity.Cliente;
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

    static String notFound = "Registro não encontrado";
    static String  cpfNotFound = "CPF não encontrado";
    static String      cpfDuplicated = "CPF já cadastrado";
    static String       sucess = "Operação realizada com sucesso";

    public List<ClienteDTO> findAll()    {
        List<Cliente> clientes = this.clienteRepository.findAll();
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        for(Cliente i : clientes)
        {
            clientesDTO.add(modelMapper.map(i, ClienteDTO.class));
        }
        return clientesDTO;

    }

    public ClienteDTO findById(Long id)
    {
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow(()-> new RuntimeException(notFound));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    public void cadastrar(ClienteDTO clienteDTO)
    {
        Assert.isTrue(!(this.clienteRepository.alreadyExists(clienteDTO.getCpf())), cpfNotFound);
        this.clienteRepository.save(modelMapper.map(clienteDTO, Cliente.class));
    }

    public String editar(ClienteDTO clienteDTO, Long id)
    {
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow(()-> new RuntimeException(notFound));
        if(this.clienteRepository.alreadyExists(clienteDTO.getCpf()))
        {
           return  cpfDuplicated;
        }
        modelMapper.map(clienteDTO,cliente);
        this.clienteRepository.save(cliente);
        return sucess;

    }

    public boolean deletar(Long id)
    {
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow(()-> new RuntimeException(notFound));
        cliente.setAtivo(false);
        this.clienteRepository.save(cliente);
        return true;
    }

}
