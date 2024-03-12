package ru.berdnikov.musicbackend.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
@EnableCaching // Включаем поддержку кэширования
public class AlbumsAPI implements APIInterface{

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private final RestTemplate restTemplate;

    public AlbumsAPI(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("albums")
    @GetMapping("/albums/{id}")
    public ResponseEntity<String> get(@PathVariable Long id) {
        return restTemplate.getForEntity(BASE_URL + "albums/" + id, String.class);
    }

    @CacheEvict(value = "albums", allEntries = true)
    @PostMapping("/albums")
    public ResponseEntity<String> create(@RequestBody String value) {
        return restTemplate.postForEntity(BASE_URL + "albums", value, String.class);
    }

    @CacheEvict(value = "albums", key = "#id")
    @PutMapping("/albums/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody String value) {
        return restTemplate.postForEntity(BASE_URL + "albums/" + id, value, String.class);
    }

    @CacheEvict(value = "albums", key = "#id")
    @DeleteMapping("/albums/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restTemplate.delete(BASE_URL + "albums/" + id);
        return ResponseEntity.noContent().build();
    }
}
