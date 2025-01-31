package io.aws.airboss.users;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.aws.airboss.profiles.Profile;
import io.aws.airboss.roles.Role;
import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @OneToOne(cascade = CascadeType.ALL, fetch =FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", nullable = false)
    @JsonManagedReference
    private Profile profile;
    
 
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
          name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
    
    
    
    // Constructor to accept a String argument
    public User(String userId) {
        this.userId = Long.parseLong(userId);
    }
    
    // Constructor to accept a Long argument
    public User(Long userId, String username, String password, Profile profile, Set<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.profile = profile;
        this.roles = roles;
    }
    
   
    // Default constructor
    public User() {
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Profile getProfile() {
        return profile;
    }
    
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
    public Set<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
  
}
