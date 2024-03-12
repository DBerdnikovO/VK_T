package ru.berdnikov.musicbackend.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.berdnikov.musicbackend.model.User;

import java.util.Objects;

public class SecurityUserhelper {
    /**
     * get user id of signed user from spring security context
     * @return user id of signed user
     */
    public static SecurityUser user() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // try-catch is a workaround for handling user while running tests
        // it only gives ClassCastException when running tests
        try {

            return ((SecurityUser) auth.getPrincipal());

        } catch (ClassCastException e) {
            return null;
        }

    }

    public static int userId() {
        return Objects.requireNonNull(user()).getUserId();
    }
}
