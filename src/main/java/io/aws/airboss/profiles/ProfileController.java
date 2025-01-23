package io.aws.airboss.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    
    @Autowired
    private ProfileService profileService;
    
    @PostMapping
    public ResponseEntity<ProfileResponseDTO> createProfile(@RequestBody ProfileRequestDTO profileRequestDTO) {
        return ResponseEntity.ok(profileService.createProfile(profileRequestDTO));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> getProfileById(@PathVariable Long id) {
        // Validate the ID before processing
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID debe ser un número positivo.");
        }
        return ResponseEntity.ok(profileService.getProfileById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable Long id, @RequestBody ProfileRequestDTO updatedProfileDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID debe ser un número positivo.");
        }
        return ResponseEntity.ok(profileService.updateProfile(id, updatedProfileDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID debe ser un número positivo.");
        }
        profileService.deleteProfile(id);
        return ResponseEntity.ok("Perfil eliminado correctamente");
    }
}
