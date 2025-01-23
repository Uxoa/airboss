package io.aws.airboss.auth;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.expiration}")
    private int jwtExpirationMs;
    
    private SecretKey key;
    
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
    
    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
              .setSubject(username)
              .claim("roles", roles) // Incluye roles en el payload
              .setIssuedAt(new Date())
              .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
              .signWith(key, SignatureAlgorithm.HS256)
              .compact();
    }
    
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
              .setSigningKey(key).build()
              .parseClaimsJws(token)
              .getBody()
              .getSubject();
    }
    
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        }
        return false;
    }
    
    
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();
            return expiration.before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }
    
    // Nuevo método para obtener la clave de firma
    public SecretKey getKey() {
        return this.key;
    }
}
