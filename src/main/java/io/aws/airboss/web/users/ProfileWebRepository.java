package io.aws.airboss.web.users;

import io.aws.airboss.profiles.Profile;
import io.aws.airboss.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProfileWebRepository extends JpaRepository<Profile, Long> {
    
    // Encuentra un usuario por su nombre de usuario a través del perfil
    @Query("SELECT p.lastLogin FROM Profile p WHERE p.user.username = :username")
    Optional<LocalDateTime> findLastLoginByUsername(@Param("username") String username);
    
}
