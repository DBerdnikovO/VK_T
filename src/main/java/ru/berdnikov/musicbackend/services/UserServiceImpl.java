package ru.berdnikov.musicbackend.services;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.berdnikov.musicbackend.model.User;
import ru.berdnikov.musicbackend.repository.UserRepository;
import ru.berdnikov.musicbackend.security.SecurityUser;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Logger logger;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Logger logger) {
        this.userRepository = userRepository;
        this.logger = logger;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        logger.debug("Entering in loadUserByUsername Method...");
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> findUserById(int id) {
        logger.debug("Entering in findUserById Method...");
        return userRepository.findUserById(id);
    }

    @Override
    public List<User> findAll() {
        logger.debug("Entering in findAll Method...");
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if(userRepository.existsUserByEmail(user.getEmail())){
            logger.error("User with this email already exists");
        } else {
            userRepository.save(user);
            logger.info("User added successfully");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findUserByEmail(username);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new SecurityUser(user.get());
    }
}
