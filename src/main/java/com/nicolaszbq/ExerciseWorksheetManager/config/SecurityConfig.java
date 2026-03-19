package com.nicolaszbq.ExerciseWorksheetManager.config;

import com.nicolaszbq.ExerciseWorksheetManager.infra.security.JwtAuthFilter;
import com.nicolaszbq.ExerciseWorksheetManager.infra.security.JwtService;
import com.nicolaszbq.ExerciseWorksheetManager.infra.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Recursos estáticos públicos
                        .requestMatchers("/", "/*.html", "/css/**", "/js/**", "/uploads/**", "/images/**").permitAll()
                        // Auth pública


                        .requestMatchers(HttpMethod.GET, "/users/**").permitAll()

                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/register").permitAll()

                        // Fotos (upload de foto é chamado após registro)
                        .requestMatchers(HttpMethod.POST, "/users/uploadPhoto/**").permitAll()
                        // Regras de role
                        .requestMatchers(HttpMethod.GET, "/worksheets/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/worksheets/**").hasRole("TRAINER")
                        .requestMatchers(HttpMethod.PUT, "/worksheets/**").hasRole("TRAINER")
                        .requestMatchers(HttpMethod.POST, "/worksheets/**").hasRole("TRAINER")
                        // O resto exige autenticação
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


}
