package io.aws.airboss.users;



import io.aws.airboss.profiles.ProfileResponseDTO;

import java.util.ArrayList;
import java.util.List;


public class UserResponseDTO {
    private Long id;
    private String username;
    private ProfileResponseDTO profile;
    private List<String> roles;
    
    public UserResponseDTO(Long id, String username, ProfileResponseDTO profile) {
        this.id = id;
        this.username = username;
        this.profile = profile;
        this.roles = new ArrayList<>();
    }
    
    public UserResponseDTO(String testuser) {
    }
    
    public UserResponseDTO(Long id, String username, String name, String lastName, String email, Long mobile, List<String> roles) {
    }
    
    
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public ProfileResponseDTO getProfile() {
        return profile;
    }
    
    public void setProfile(ProfileResponseDTO profile) {
        this.profile = profile;
    }
    
    public List<String> getRoles() {
        return roles;
    }
    
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    public String getUsername() {
        return username;
    }
}