package ru.berdnikov.musicbackend.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import ru.berdnikov.musicbackend.utils.password.ValidPassword;

public class UserDTO {
    @NotBlank(message = "Username can't be empty")
    private String username;
    @Email
    private String email;
    @NotBlank(message = "Password can't be empty")
    @ValidPassword
    private String password;

    public UserDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
