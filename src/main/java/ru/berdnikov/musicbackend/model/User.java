package ru.berdnikov.musicbackend.model;

import jakarta.persistence.*;


@Entity
@Table(name = "person")
public class User extends AuditorEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private char[] password;

    @OneToOne(fetch = FetchType.EAGER)
    private Role role;

    public User() {
    }

    public User(String name, String email, char[] password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


//    private void audit(String operation) {
//        setOperation(operation);
//        setTimestamp((new Date()).getTime());
//    }
}
