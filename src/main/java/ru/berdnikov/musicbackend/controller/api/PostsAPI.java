package ru.berdnikov.musicbackend.controller.api;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
public class PostsAPI implements APIInterface {
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private final RestTemplate restTemplate;

    public PostsAPI(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("posts")
    @GetMapping("/posts/{id}")
    public ResponseEntity<String> get(@PathVariable Long id) {
        return restTemplate.getForEntity(BASE_URL + "posts/" + id, String.class);
    }

    @CacheEvict(value = "posts", allEntries = true)
    @PostMapping("/posts")
    public ResponseEntity<String> create(@RequestBody String post) {
        return restTemplate.postForEntity(BASE_URL + "posts", post, String.class);
    }

    @CacheEvict(value = "posts", key = "#id")
    @PutMapping("/posts/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody String post) {
        return restTemplate.postForEntity(BASE_URL + "posts/" + id, post, String.class);
    }

    @CacheEvict(value = "posts", key = "#id")
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restTemplate.delete(BASE_URL + "posts/" + id);
        return ResponseEntity.noContent().build();
    }
}
