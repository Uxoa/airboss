package io.aws.airboss.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }
    
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
              .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + name));
    }
    
    public String getRoleName(String role) {
        return getRoleByName(role).getName();
    }
}
