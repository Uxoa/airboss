package io.aws.airboss.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtils;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    
    @Override
    public void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt) && !jwtUtils.isTokenExpired(jwt)) {
                // Obtener el nombre de usuario del token
                String username = jwtUtils.getUsernameFromToken(jwt);
                
                // Extraer los roles del token
                Claims claims = Jwts.parserBuilder()
                      .setSigningKey(jwtUtils.getKey())
                      .build()
                      .parseClaimsJws(jwt)
                      .getBody();
                
                @SuppressWarnings("unchecked")
                List<SimpleGrantedAuthority> authorities = ((List<?>) claims.get("roles")).stream()
                      .map(role -> new SimpleGrantedAuthority((String) role))
                      .collect(Collectors.toList());
                
                // Cargar detalles del usuario
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                // Crear autenticación
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                      userDetails,
                      null,
                      authorities
                );
                
                // Establecer detalles de la solicitud
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Establecer el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Authentication: " + SecurityContextHolder.getContext().getAuthentication());
                
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
