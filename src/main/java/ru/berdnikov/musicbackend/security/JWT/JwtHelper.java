package ru.berdnikov.musicbackend.security.JWT;

import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.Map;

public interface JwtHelper {
    Claims getAllClaimsFromToken(String token);
    String getEmailFromToken(String token);
    Date getExpirationDateFromToken(String token);
    String generateToken(String email);
    Boolean validateToken(String token, String tokenEmail);
}
