package io.aws.airboss.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                    // Permitir el acceso sin autenticación a auth endpoints
                    .requestMatchers("/api/auth/**").permitAll()
                    // Configurar permisos para /api/users
                    .requestMatchers("/api/users").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER") // Para el acceso general
                    .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN") // Solo los admins pueden modificar
                    .requestMatchers(HttpMethod.PUT,"/api/users/{id}").hasAuthority("ROLE_ADMIN")
                    .requestMatchers(HttpMethod.DELETE,"/api/users/{id}").hasAuthority("ROLE_ADMIN")
                    .requestMatchers(HttpMethod.POST,"/api/bookings/confirm/{id}").hasAuthority("ROLE_ADMIN")
                    .requestMatchers(HttpMethod.POST,"/api/bookings/cancel/{id}").hasAuthority("ROLE_ADMIN")
                    .requestMatchers(HttpMethod.POST,"/api/bookings/me").hasAuthority("ROLE_ADMIN")
                    // Bloquear cualquier otra solicitud
                    .anyRequest().authenticated()
              )
              // Configuración de manejo de sesión
              .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              // Deshabilitar CSRF y CORS (si no son necesarios)
              .csrf(csrf -> csrf.disable())
              .cors(cors -> cors.disable())
              // Agregar el filtro JWT
              .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        
        // Manejo de excepciones para errores de autenticación y acceso denegado
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
