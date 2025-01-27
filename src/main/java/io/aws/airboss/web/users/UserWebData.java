package io.aws.airboss.web.users;

import java.time.LocalDateTime;

public class UserWebData {
    
    private final String username;
    private final String email;
    private final String role;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastLogin;
    private boolean enabled;
    
    
    public UserWebData(String username, String email, String role, LocalDateTime createdAt, LocalDateTime lastLogin, boolean enabled) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.enabled = enabled;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getRole() {
        return role;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
