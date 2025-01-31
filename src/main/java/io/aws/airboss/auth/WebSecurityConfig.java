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
                    .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**", "/error").permitAll()
                    .requestMatchers(
                          "/perfil",
                          "/perfil/**",
                          "/reservas",
                          "/reservas/**",
                          "/mybookings",
                          "/mybookings/**",
                          "/usuario",
                          "/usuario/**",
                          "/details/**",
                          "/dashboard",
                          "/vuelos",
                          "/vuelos/**",
                          "/search",
                          "/search/**",
                          "/search/results",
                          "/bookings/confirm",
                          "/bookings/confirm/**",
                          "/bookings/cancel/**",
                          "/bookings/**",
                          "/bookings/search",
                          "/confirm",
                          "/confirm/**",
                          "/aeropuertos",
                          "/calendario/**",
                          "/airports/**",
                          "/api/flights/available-dates",
                          "/api/flights/search",
                          "/api/flights/search/confirm",
                          "/api/flights/search/results",
                          "/api/flights/confirm/**",
                          "/api/flights/**",
                          "/api/aeropuertos/**",
                          "/api/bookings/create"
                          
                    ).authenticated()
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN")
                    .requestMatchers("/api/bookings/**", "/api/flights/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                    .requestMatchers("/api/reservations/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER") // Si tienes un módulo de reservas
                    .anyRequest().authenticated()
              )
              .formLogin(formLogin -> formLogin
                    .loginPage("/login")
                    .loginProcessingUrl("/process-login")
                    .defaultSuccessUrl("/dashboard", true)
                    .failureUrl("/login?error=true")
                    .permitAll()
              )
              .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
              )
              .cors(cors -> cors.disable())
              .csrf(csrf -> csrf.disable());
        
        return http.build();
    }
    
}
