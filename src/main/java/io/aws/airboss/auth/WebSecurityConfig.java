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
                    .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                    .requestMatchers("/profile", "/bookings/view").authenticated()
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN")
                    .requestMatchers("/api/bookings/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                    .anyRequest().authenticated()
              )
              .formLogin(formLogin -> formLogin
                    .loginPage("/login") // Página de inicio de sesión personalizada
                    .loginProcessingUrl("/process-login") // URL para procesar el formulario de login
                    .defaultSuccessUrl("/dashboard", true) // Redirige a /dashboard tras iniciar sesión
                    .failureUrl("/login?error=true") // En caso de error en el login
                    .permitAll()
              )
              .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL de logout
                    .logoutSuccessUrl("/login?logout") // Redirige tras cerrar sesión
                    .permitAll()
              )
              .cors(cors -> cors.disable())
              .csrf(csrf -> csrf.disable());
        
        return http.build();
    }
    
    
    
    
}
