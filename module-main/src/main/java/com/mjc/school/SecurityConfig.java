package com.mjc.school;

import com.mjc.school.service.impl.securityServices.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.mjc.school.service.security.Permission.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.mvcMatchers("");
                            auth.mvcMatchers("/news-management-api/v1/login").permitAll();
                            auth.mvcMatchers("/news-management-api/v1/signup").permitAll();

                            auth.mvcMatchers(HttpMethod.GET, "/news-management-api/v1/news/**").permitAll();
                            auth.mvcMatchers(HttpMethod.POST, "/news-management-api/v1/news/**").hasAuthority(NEWS_CREATE.toString());
                            auth.mvcMatchers(HttpMethod.DELETE, "/news-management-api/v1/news/**").hasAuthority(NEWS_DELETE.toString());
                            auth.mvcMatchers(HttpMethod.PATCH, "/news-management-api/v1/news/**").hasAuthority(NEWS_UPDATE.toString());

                            auth.mvcMatchers(HttpMethod.GET, "/news-management-api/v1/comments/**").hasAuthority(COMMENTS_READ.toString());
                            auth.mvcMatchers(HttpMethod.POST, "/news-management-api/v1/comments/**").hasAuthority(COMMENTS_CREATE.toString());
                            auth.mvcMatchers(HttpMethod.DELETE, "/news-management-api/v1/comments/**").hasAuthority(COMMENTS_DELETE.toString());
                            auth.mvcMatchers(HttpMethod.PATCH, "/news-management-api/v1/comments/**").hasAuthority(COMMENTS_UPDATE.toString());

                            auth.mvcMatchers(HttpMethod.GET, "/news-management-api/v1/authors/**").hasAuthority(AUTHORS_READ.toString());
                            auth.mvcMatchers(HttpMethod.POST, "/news-management-api/v1/authors/**").hasAuthority(AUTHORS_CREATE.toString());
                            auth.mvcMatchers(HttpMethod.DELETE, "/news-management-api/v1/authors/**").hasAuthority(AUTHORS_DELETE.toString());
                            auth.mvcMatchers(HttpMethod.PATCH, "/news-management-api/v1/authors/**").hasAuthority(AUTHORS_UPDATE.toString());

                            auth.mvcMatchers(HttpMethod.GET, "/news-management-api/v1/tags/**").hasAuthority(TAGS_READ.toString());
                            auth.mvcMatchers(HttpMethod.POST, "/news-management-api/v1/tags/**").hasAuthority(TAGS_CREATE.toString());
                            auth.mvcMatchers(HttpMethod.DELETE, "/news-management-api/v1/tags/**").hasAuthority(TAGS_DELETE.toString());
                            auth.mvcMatchers(HttpMethod.PATCH, "/news-management-api/v1/tags/**").hasAuthority(TAGS_UPDATE.toString());
                        }
                )
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuth = new DaoAuthenticationProvider();
        daoAuth.setUserDetailsService(userService);
        daoAuth.setPasswordEncoder(passwordEncoder());
        return daoAuth;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
