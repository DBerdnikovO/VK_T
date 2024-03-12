package ru.berdnikov.musicbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.berdnikov.musicbackend.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String name);
    Optional<User> findUserById(int id);
    @NonNull
    List<User> findAll();
    boolean existsUserByEmail(String email);
}
