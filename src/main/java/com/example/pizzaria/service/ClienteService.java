package com.example.pizzaria.service;

import com.example.pizzaria.dto.ClienteDTO;
import com.example.pizzaria.entity.Cliente;
import com.example.pizzaria.entity.Funcionario;
import com.example.pizzaria.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
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

    static final String DELETED = "Cliente deletado com sucesso";
    static final String DISABLED = "Cliente desativado com sucesso";

    static final String FAIL = "Registro não encontrado";




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

    public ClienteDTO findByCpf(String cpf)
    {
        Cliente cliente = this.clienteRepository.findClienteByCpf(cpf);
        if(cliente == null)
        {
            throw new RuntimeException(NOTFOUND);
        }
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    public ClienteDTO cadastrar(ClienteDTO clienteDTO)
    {
        Assert.isTrue(!(this.clienteRepository.alreadyExists(clienteDTO.getCpf())), CPFNOTFOUND);
        Cliente clienteSalvo = this.clienteRepository.save(modelMapper.map(clienteDTO, Cliente.class));
        return modelMapper.map(clienteSalvo, ClienteDTO.class);
    }

    public ClienteDTO editar(ClienteDTO clienteDTO, Long id)    {
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow(()-> new RuntimeException(NOTFOUND));
        if(this.clienteRepository.alreadyExists(clienteDTO.getCpf()))
        {
            Assert.isTrue( this.clienteRepository.isTheSame(clienteDTO.getCpf()).equals(id) , CPFDUPLICATED);
        }
        modelMapper.map(clienteDTO,cliente);
        Cliente clienteSalvo = this.clienteRepository.save(cliente);
        return modelMapper.map(clienteSalvo, ClienteDTO.class);

    }

    public String deletar(Long id) {
        if (!clienteRepository.doesExist(id)) {
            throw new IllegalArgumentException(FAIL);
        } else if (this.clienteRepository.existsInPedido(id) || this.clienteRepository.existsInEndereco(id)) {
            Cliente cliente = this.clienteRepository.findById(id).orElseThrow(()-> new RuntimeException(FAIL));
            cliente.setAtivo(false);
            this.clienteRepository.save(cliente);
            return DISABLED;
        } else {
            this.clienteRepository.deleteById(id);
            return DELETED;
        }
    }

}
