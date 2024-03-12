package ru.berdnikov.musicbackend.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.berdnikov.musicbackend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(int id);
    List<User> findAll();
    void saveUser(User user);
}
