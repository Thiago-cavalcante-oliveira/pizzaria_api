package com.example.pizzaria.service;

import com.example.pizzaria.config.JwtServiceGenerator;
import com.example.pizzaria.dto.LoginDTO;
import com.example.pizzaria.dto.UserDTO;
import com.example.pizzaria.entity.User;
import com.example.pizzaria.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginRepository repository;
    @Autowired
    private JwtServiceGenerator jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;


    public UserDTO logar(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getLogin(),
                        loginDTO.getPassword()
                )
        );
        User user = repository.findByUsername(loginDTO.getLogin()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return toUserDTO(user, jwtToken);
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
