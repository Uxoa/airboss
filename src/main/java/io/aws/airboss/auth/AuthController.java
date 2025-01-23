package io.aws.airboss.auth;

import io.aws.airboss.users.User;
import io.aws.airboss.auth.JwtUtil;
import io.aws.airboss.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtils;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody User user) {
        // Autenticar al usuario
        Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword()
              )
        );
        
        // Obtener detalles del usuario y roles
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
              .map(auth -> auth.getAuthority())
              .collect(Collectors.toList());
        
        // Generar token con roles
        String token = jwtUtils.generateToken(userDetails.getUsername(), roles);
        
        
        // Crear respuesta JSON
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("message", "Login successful");
        return ResponseEntity.ok(response);
    }
    
    
    
    @PostMapping("/token")
    public String token(Authentication authentication) {
        return jwtUtils.generateToken(authentication.getName(), authentication.getAuthorities().stream()
              .map(auth -> auth.getAuthority())
              .collect(Collectors.toList()));
    }
    
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            try {
                // Validar token y asegurarse de que está expirado
                if (jwtUtils.validateJwtToken(jwtToken) && jwtUtils.isTokenExpired(jwtToken)) {
                    String username = jwtUtils.getUsernameFromToken(jwtToken);
                    
                    // Obtener roles del usuario
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                    List<String> roles = userDetails.getAuthorities().stream()
                          .map(auth -> auth.getAuthority())
                          .collect(Collectors.toList());
                    
                    // Generar nuevo token con roles
                    String newToken = jwtUtils.generateToken(username, roles);
                    
                    Map<String, String> response = new HashMap<>();
                    response.put("token", newToken);
                    response.put("message", "Token refreshed successfully");
                    return ResponseEntity.ok(response);
                }
            } catch (JwtException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token format");
    }
    
    
    
    
    @PostMapping("/signup")
    public String registerUser(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Error: Username is already taken!";
        }
        // Create new user's account
        User newUser = new User(
        );
        userRepository.save(newUser);
        return "User registered successfully!";
    }
    
    
}