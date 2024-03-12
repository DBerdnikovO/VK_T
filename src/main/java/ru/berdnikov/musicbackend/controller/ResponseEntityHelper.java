package ru.berdnikov.musicbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.berdnikov.musicbackend.dto.ErrorResponseDTOImplDTO;
import ru.berdnikov.musicbackend.dto.JwtResponseDTOImplDTO;
import ru.berdnikov.musicbackend.dto.ResponseDTO;

public class ResponseEntityHelper {
    protected static ResponseEntity getSuccessfulResponse(String token) {
        return ResponseEntity.ok(JwtResponseDTOImplDTO.builder()
                .accessToken(token)
                .build());
    }

    protected static ResponseEntity getBadRequestResponse(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseDTOImplDTO.builder()
                .error(message)
                .build());
    }
}
