package io.aws.airboss.users;

import io.aws.airboss.profiles.*;
import io.aws.airboss.roles.Role;
import io.aws.airboss.roles.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder; // Inyección del PasswordEncoder
    
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
    
    @Transactional
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
              .map(userMapper::toResponseDTO) // Mapea cada entidad a un DTO
              .collect(Collectors.toList());
    }
    
    @Transactional
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return userMapper.toResponseDTO(user);
    }
    
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        // Obtener el usuario existente
        User userToUpdate = userRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Actualizar propiedades del usuario
        userToUpdate.setUsername(userRequestDTO.getUsername());
        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isBlank()) {
            // Codificar la nueva contraseña si se envía
            userToUpdate.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }
        
        // Obtener y asignar los roles desde la base de datos
        List<Role> roles = userRequestDTO.getRoles().stream()
              .map(roleName -> roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleName)))
              .collect(Collectors.toList());
        userToUpdate.setRoles(new HashSet<>(roles));
        
        // Actualizar el perfil asociado
        Profile profileToUpdate = userToUpdate.getProfile();
        if (profileToUpdate == null) {
            throw new RuntimeException("El perfil asociado no se encontró");
        }
        ProfileRequestDTO profileDTO = userRequestDTO.getProfile();
        profileToUpdate.setName(profileDTO.getName());
        profileToUpdate.setLastName(profileDTO.getLastName());
        profileToUpdate.setEmail(profileDTO.getEmail());
        profileToUpdate.setMobile(profileDTO.getMobile());
        profileToUpdate.setProfileImage(profileDTO.getProfileImage());
        
        // Guardar los cambios
        userRepository.save(userToUpdate);
        
        return userMapper.toResponseDTO(userToUpdate);
    }
    
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
    
    @Transactional
    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return userMapper.toResponseDTO(user);
    }
}
