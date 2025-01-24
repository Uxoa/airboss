package io.aws.airboss.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
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
import org.springframework.web.cors.CorsConfigurationSource;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ConfigurableWebServerFactory configurableWebServerFactory, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
              
              .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                    // Permitir acceso sin autenticación a las rutas públicas
                    .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                    // Asegurar rutas para vistas Thymeleaf
                    .requestMatchers("/profile", "/bookings/view").authenticated()
                    // Configurar permisos para la API
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN")
                    .requestMatchers("/api/bookings/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                    .anyRequest().authenticated()
              )
              .formLogin(formLogin -> formLogin
                    .loginPage("/login") // Página personalizada de inicio de sesión
                    .loginProcessingUrl("/process-login") // Endpoint para procesar el login
                    .defaultSuccessUrl("/", true) // Redirección a la página principal tras iniciar sesión
                    .failureUrl("/login?error=true") // Redirección en caso de error
                    .permitAll() // Permitir acceso sin autenticación
              )
              
              
              .cors(cors -> cors.disable())
              .csrf(csrf -> csrf.disable())
              
              
              .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
              );
        
        
        return http.build();
    }

    
}
