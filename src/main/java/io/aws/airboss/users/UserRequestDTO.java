package io.aws.airboss.users;



import io.aws.airboss.profiles.ProfileRequestDTO;

import java.util.List;


public class UserRequestDTO {
    private String username;
    private String password;
    private String profileName;
    private String profileLastName;
    private String profileEmail;
    private Long profileMobile;
    private ProfileRequestDTO profile;
    private List<String> roles;
    
    
    public UserRequestDTO(String username, String password, String profileName, String profileLastName, String profileEmail, Long profileMobile, List<String> roles) {
        this.username = username;
        this.password = password;
        this.profileName = profileName;
        this.profileLastName = profileLastName;
        this.profileEmail = profileEmail;
        this.profileMobile = profileMobile;
        this.roles = roles;
    }
    
    public UserRequestDTO() {
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getProfileName() {
        return profileName;
    }
    
    public String getProfileLastName() {
        return profileLastName;
    }
    
    public String getProfileEmail() {
        return profileEmail;
    }
    
    public Long getProfileMobile() {
        return profileMobile;
    }
    
    public List<String> getRoles() {
        return roles;
    }
    
    public ProfileRequestDTO getProfile() {
        return profile;
    }
    

}
