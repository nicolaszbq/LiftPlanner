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
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Recursos estáticos públicos
                        .requestMatchers("/", "/index.html","/login.html","/register.html",  "/css/**", "/js/**", "/uploads/**", "/images/**", "/caminho/**").permitAll()
                        // Auth pública
                        .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        //permitindo todas requisições ao gemini (apenas para testar)

                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/register").permitAll()

                        // Fotos (upload de foto é chamado após registro)
                        .requestMatchers(HttpMethod.POST, "/users/uploadPhoto/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/uploadPhoto/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        // Regras de role
                        .requestMatchers(HttpMethod.GET, "/dashboard.html").hasRole("TRAINER")
                        .requestMatchers(HttpMethod.GET, "/userArea.html").hasRole("MEMBER")

                        .requestMatchers(HttpMethod.POST, "/worksheets/update/**").hasRole("TRAINER")
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
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of(
            "http://localhost:*",
            "https://liftplanner.onrender.com"
        ));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization","Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


}
