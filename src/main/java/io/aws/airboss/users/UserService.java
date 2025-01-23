package io.aws.airboss.users;

import io.aws.airboss.profiles.ProfileMapper;
import io.aws.airboss.profiles.*;
import io.aws.airboss.roles.Role;
import io.aws.airboss.roles.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Transactional
    public User createUser(User user, Profile profile, List<String> roles) {
        // Asignar roles
        Set<Role> roleEntities = roles.stream()
              .map(roleName -> roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleName)))
              .collect(Collectors.toSet());
        user.setRoles(roleEntities);
        
        // Asociar perfil al usuario
        profile.setUser(user);
        profileRepository.save(profile);
        
        // Guardar usuario
        return userRepository.save(user);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}