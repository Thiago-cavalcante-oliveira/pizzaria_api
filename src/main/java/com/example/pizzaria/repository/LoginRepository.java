package com.example.pizzaria.repository;

import com.example.pizzaria.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String login);
}
