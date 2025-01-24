package io.aws.airboss.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
        http
              .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                    // Permitir acceso sin autenticación a las rutas públicas
                    .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                    // Asegurar rutas para vistas Freemarker
                    .requestMatchers("/profile", "/bookings/view").authenticated()
                    // Configurar permisos para API
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN")
                    .requestMatchers("/api/bookings/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                    .anyRequest().authenticated() // Todas las demás rutas requieren autenticación
              )
              .formLogin(formLogin -> formLogin
                    .loginPage("/login") // Página personalizada de inicio de sesión
                    .loginProcessingUrl("/process-login") // Endpoint para procesar el login
                    .defaultSuccessUrl("/", true) // Redirección a la página principal tras iniciar sesión
                    .failureUrl("/login?error=true") // Redirección en caso de error
                    .permitAll() // Permitir acceso sin autenticación
              )
              .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
              )
              .cors(cors -> cors.disable())
              .csrf(csrf -> csrf.disable()); // Si necesitas habilitar CSRF, ajusta esta línea
        
        return http.build();
    }
    
    
    
    
}
