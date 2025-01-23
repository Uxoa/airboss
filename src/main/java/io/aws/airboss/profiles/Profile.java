package io.aws.airboss.profiles;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.aws.airboss.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;


import java.time.LocalDateTime;

@Entity
@Table(name = "profiles")
public class Profile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private Long mobile;
    
    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private User user;
    
    @Column(nullable = false)
    private String profileImage;
    
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
    
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    
    public Profile(Long profileId, String name, String lastName, String email, Long mobile,
                   User user,String profileImage , LocalDateTime registrationDate, LocalDateTime lastLogin) {
        this.profileId = profileId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.user = user;
        this.profileImage = profileImage;
        this.registrationDate = registrationDate;
        this.lastLogin = lastLogin;
    }
    
    public Profile() {
        
    }
    
    public Profile(String profileName, String profileLastName, String profileEmail, Long profileMobile) {
    }
    
    // Getters y Setters
    public Long getProfileId() {
        return profileId;
    }
    
    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Long getMobile() {
        return mobile;
    }
    
    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getProfileImage() {
        return profileImage;
    }
    
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    @Override
    public String toString() {
        return "Profile{" +
              "profileId=" + profileId +
              ", name='" + name + '\'' +
              ", lastName='" + lastName + '\'' +
              ", email='" + email + '\'' +
              ", mobile=" + mobile +
              ", user=" + user +
              ", registrationDate=" + registrationDate +
              ", lastLogin=" + lastLogin +
              '}';
    }
    
  
}
