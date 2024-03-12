package ru.berdnikov.musicbackend.utils.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface ValidPassword {
    String message() default "The password must consist of at least 6 digits and 2 letters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}