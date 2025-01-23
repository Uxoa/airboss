package io.aws.airboss.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {
    
    private final AuthEntryPointJwt unauthorizedHandler;
    
    public WebSecurityConfig(AuthEntryPointJwt unauthorizedHandler) {
        this.unauthorizedHandler = unauthorizedHandler;
    }
    
    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                    .requestMatchers("/api/auth/**").permitAll() // Permitir acceso a auth sin autenticación
                    .requestMatchers("/api/users").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                    .anyRequest().authenticated()
              )
              .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .csrf(csrf -> csrf.disable())
              .cors(cors -> cors.disable())
              .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        
        
        http.exceptionHandling(exceptionHandling -> exceptionHandling
              .authenticationEntryPoint(unauthorizedHandler)
              .accessDeniedHandler((request, response, accessDeniedException) -> {
                  response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                  response.getWriter().write("Access Denied: " + accessDeniedException.getMessage());
              })
        );
        
        return http.build();
    }
}
