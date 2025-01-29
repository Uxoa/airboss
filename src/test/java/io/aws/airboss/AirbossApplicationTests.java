package io.aws.airboss;

import io.aws.airboss.auth.*;
import io.aws.airboss.users.*;
import io.aws.airboss.roles.Role;
import io.aws.airboss.profiles.Profile;
import io.aws.airboss.profiles.ProfileRepository;
import io.aws.airboss.roles.RoleRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirbossApplicationTests {
    
    @Mock private AuthenticationManager authenticationManager;
    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder encoder;
    @Mock private JwtUtil jwtUtils;
    @Mock private CustomUserDetailsService customUserDetailsService;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain filterChain;
    @Mock private RoleRepository roleRepository;
    @Mock private ProfileRepository profileRepository;
    @Mock private UserMapper userMapper;
    
    @InjectMocks private AuthController authController;
    @InjectMocks private AuthTokenFilter authTokenFilter;
    @InjectMocks private UserService userService;
    
    private User user;
    private Profile profile;
    
    @BeforeEach
    void setUp() {
        profile = new Profile();
        profile.setName("John");
        profile.setLastName("Doe");
        profile.setEmail("john.doe@example.com");
        
        user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setProfile(profile);
        SecurityContextHolder.clearContext();
    }
    
    /*** AUTH TESTS ***/
    
    @Test
    void testAuthenticateUser() {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
              .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser");
        when(jwtUtils.generateToken(eq("testuser"), anyList())).thenReturn("mockedToken");
        
        ResponseEntity<Map<String, String>> response = authController.authenticateUser(user);
        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("mockedToken", response.getBody().get("token"));
    }
    
    @Test
    void testRegisterUser() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        String response = authController.registerUser(user);
        
        assertEquals("User registered successfully!", response);
    }
    
    @Test
    void testRegisterUserWithExistingUsername() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);
        
        String response = authController.registerUser(user);
        
        assertEquals("Error: Username is already taken!", response);
    }
    
}
