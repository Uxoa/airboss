package io.aws.airboss.auth;

import  io.aws.airboss.users.User;
import  io.aws.airboss.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class CustomUserDetailsService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
              user.get().getUsername(),
              user.get().getPassword(),
              user.get().getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName())) // Asegúrate de usar `role.getName()`
                    .collect(Collectors.toList())
        );
        
        
    }
}