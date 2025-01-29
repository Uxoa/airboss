package io.aws.airboss.users;



import io.aws.airboss.profiles.ProfileRequestDTO;

import java.util.List;


public class UserRequestDTO {
    private String username;
    private String password; // Opcional para actualizaciones
    private List<String> roles;
    private ProfileRequestDTO profile;
    

    
    public UserRequestDTO() {
    }
    
    
    
    public String getUsername() {
        return username;
    }
    
    
    
    public String getPassword() {
        return password;
    }
    
  
    public List<String> getRoles() {
        return roles;
    }
    
    public ProfileRequestDTO getProfile() {
        return profile;
    }
    
    
    public void setUsername(String updatedUser) {
        this.username = updatedUser;
    }
    
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    
    public void setRoles(List<String> roleAdmin) {
        this.roles = roleAdmin;
        
    }
    
    public void setProfile(ProfileRequestDTO profile) {
        this.profile = profile;
    }
    
    
}
