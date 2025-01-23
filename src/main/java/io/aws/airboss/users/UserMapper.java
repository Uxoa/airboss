package io.aws.airboss.users;

import io.aws.airboss.users.User;
import io.aws.airboss.roles.Role;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserMapper {
    
    public UserResponseDTO toResponseDTO(User user) {
        if (user == null) {
            return null;
        }
        
       UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername(user.getUsername());
        // Map other fields as needed
        return userResponseDTO;
    }
    
    public User mapToEntity(UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) {
            return null;
        }
        
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(userRequestDTO.getPassword());
        // Map other fields as needed
        return user;
    }
    
    public User mapUpdateEntity(User user, UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) {
            return user;
        }
        
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(userRequestDTO.getPassword());
        // Map other fields as needed
        return user;
    }
    
    public Set<Role> map(List<String> value) {
        if (value == null) {
            return null;
        }
        
        Set<Role> roles = new HashSet<>();
        for (String roleName : value) {
            Role role = new Role();
            role.setName(roleName);
            roles.add(role);
        }
        return roles;
    }
}