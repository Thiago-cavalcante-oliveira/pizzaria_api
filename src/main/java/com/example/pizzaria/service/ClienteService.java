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

    public List<ClienteDTO> findAll()
    {
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
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow(()-> new RuntimeException("Registro não encontrado"));

        return modelMapper.map(cliente, ClienteDTO.class);
    }

    public void cadastrar(ClienteDTO clienteDTO)
    {
        Assert.isTrue(!(this.clienteRepository.alreadyExists(clienteDTO.getCpf())), "CPF já cadastrado");

        this.clienteRepository.save(modelMapper.map(clienteDTO, Cliente.class));
    }

    public void editar(ClienteDTO clienteDTO, Long id)
    {
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow(()-> new RuntimeException("Registro não encontrado"));
        if(this.clienteRepository.alreadyExists(clienteDTO.getCpf()))
        {
            Assert.isTrue( this.clienteRepository.isTheSame(clienteDTO.getCpf()).equals(id), "CPF já cadastrado");
        }

        modelMapper.map(clienteDTO,cliente);

        this.clienteRepository.save(cliente);
    }

    public boolean deletar(Long id)
    {
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow(()-> new RuntimeException("Registro não encontrado"));
        cliente.setAtivo(false);
        this.clienteRepository.save(cliente);
        return true;
    }

}
