package io.aws.airboss.security.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;

public class LoginRequest {
    private String username;
    private String password;
    
    
    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] bytes = "secret".getBytes();
        SecretKeySpec secretKey = new SecretKeySpec(bytes, "HmacSHA512");
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS512).build();
    }
    
    
    // Getters y setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
