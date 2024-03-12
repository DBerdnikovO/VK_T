package ru.berdnikov.musicbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.berdnikov.musicbackend.security.JWT.JwtAuthenticationEntryPoint;
import ru.berdnikov.musicbackend.security.JWT.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    private enum Roles {
        ADMIN,USER,POSTS,USERS,ALBUMS;
    }
    /**
     * Его основная цель - обработать ситуацию, когда аутентификация пользователя не удалась.
     */
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    /**
     * фильтр запросов, который используется для обработки JWT
     */
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtRequestFilter jwtRequestFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * passwordEncoder для хеширования паролей пользователей.
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * создаем AuthenticationManager.
     * */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * цепочка фильтров безопасности
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .exceptionHandling((exception) -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedPage("/error/access-denied"))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(UrlMapping.AUTH + UrlMapping.REGIS).permitAll()
                                .requestMatchers(UrlMapping.AUTH + UrlMapping.LOGIN).permitAll()
//                                .requestMatchers(UrlMapping.POSTS).hasAnyRole(Roles.POSTS.name(),Roles.ADMIN.name())
//                                .requestMatchers(UrlMapping.ALBUMS).hasAnyRole(Roles.ALBUMS.name(),Roles.ADMIN.name())
//                                .requestMatchers(UrlMapping.USERS).hasAnyRole(Roles.USER.name(),Roles.ADMIN.name())
                        .requestMatchers("/api/test/**").permitAll()
                        .anyRequest().authenticated()
                );
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    /**
     * конфигуратор для обработки CORS
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") //к каким URL-адресам применяется
                        .allowedMethods("*")//какие HTTP-методы разрешены
                        .allowedOrigins("*")//с каких источников
                        .allowedHeaders("*");//какие заголовки разрешено включать
            }
        };
    }
}
