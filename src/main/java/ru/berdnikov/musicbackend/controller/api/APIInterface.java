package ru.berdnikov.musicbackend.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface APIInterface {
    ResponseEntity<String> get(@PathVariable Long id);
    ResponseEntity<String> create(@RequestBody String value);
    ResponseEntity<String> update(@PathVariable Long id, @RequestBody String value);
    ResponseEntity<Void> delete(@PathVariable Long id);
}
