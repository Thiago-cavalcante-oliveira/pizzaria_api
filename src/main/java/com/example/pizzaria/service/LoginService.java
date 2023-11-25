package com.example.pizzaria.service;

import com.example.pizzaria.config.JwtServiceGenerator;
import com.example.pizzaria.dto.ClienteDTO;
import com.example.pizzaria.dto.LoginDTO;
import com.example.pizzaria.dto.UserDTO;
import com.example.pizzaria.entity.Cliente;
import com.example.pizzaria.entity.User;
import com.example.pizzaria.repository.LoginRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginRepository repository;
    @Autowired
    private JwtServiceGenerator jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO logar(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getLogin(),
                        loginDTO.getSenha()
                )
        );
        User user = repository.findByUsername(loginDTO.getLogin()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return toUserDTO(user, jwtToken);
    }

    public UserDTO createUser(LoginDTO login, ClienteDTO cliente)
    {
        User user = new User();
        user.setPassword(passwordEncoder.encode(login.getSenha()));
        user.setUsername(cliente.getCpf());
        user.setId_cliente(cliente.getId());
        user.setRole("USER");

        User userSalvo = this.repository.save(user);
        return modelMapper.map(userSalvo, UserDTO.class);
    }


    private UserDTO toUserDTO(User user, String token) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setRole(user.getRole());
        userDTO.setToken(token);
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }
}
