package ru.berdnikov.musicbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.berdnikov.musicbackend.dto.ResponseDTO;
import ru.berdnikov.musicbackend.dto.UserDTO;
import ru.berdnikov.musicbackend.model.Role;
import ru.berdnikov.musicbackend.model.User;
import ru.berdnikov.musicbackend.security.JWT.JwtHelper;
import ru.berdnikov.musicbackend.services.UserService;

import java.util.Optional;

import static ru.berdnikov.musicbackend.controller.ResponseEntityHelper.getSuccessfulResponse;
import static ru.berdnikov.musicbackend.controller.ResponseEntityHelper.getBadRequestResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtHelper jwtHelper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, JwtHelper jwtHelper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtHelper = jwtHelper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/reg")
    public ResponseEntity registerAndGetToken(@Validated @RequestBody UserDTO userDTO) {
        boolean exist = userService.findUserByEmail(userDTO.getEmail()).isPresent();
        if(exist) {
            return getBadRequestResponse("User with this email already exists");
        } else {
            userService.saveUser(generateUser(userDTO));
            String token = jwtHelper.generateToken(userDTO.getEmail());
            return getSuccessfulResponse(token);
        }
    }

    @PostMapping("/login")
    public ResponseEntity loginAndGetToken(@Validated @RequestBody UserDTO userDTO) {
        Optional<User> userOptional = userService.findUserByEmail(userDTO.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(userDTO.getPassword(), String.valueOf(user.getPassword()))) {
                String token = jwtHelper.generateToken(user.getEmail());
                return getSuccessfulResponse(token);
            } else {
                return getBadRequestResponse("Incorrect password");
            }
        } else {
            return getBadRequestResponse("User not found");
        }
    }

    private User generateUser(UserDTO userDTO) {
        Role role = new Role();
        role.setId(2);
        return new User(
                userDTO.getName(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()).toCharArray(),
                role);
    }
}
