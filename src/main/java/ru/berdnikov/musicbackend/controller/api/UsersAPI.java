package ru.berdnikov.musicbackend.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class UsersAPI implements APIInterface {
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private final RestTemplate restTemplate;

    public UsersAPI(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("users")
    @GetMapping("/users/{id}")
    public ResponseEntity<String> get(@PathVariable Long id) {
        return restTemplate.getForEntity(BASE_URL + "users/" + id, String.class);
    }

    @CacheEvict(value = "users", allEntries = true)
    @PostMapping("/users")
    public ResponseEntity<String> create(@RequestBody String value) {
        return restTemplate.postForEntity(BASE_URL + "users", value, String.class);
    }

    @CacheEvict(value = "users", key = "#id")
    @PutMapping("/users/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody String value) {
        return restTemplate.postForEntity(BASE_URL + "users/" + id, value, String.class);
    }

    @CacheEvict(value = "users", key = "#id")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restTemplate.delete(BASE_URL + "users/" + id);
        return ResponseEntity.noContent().build();
    }
}
