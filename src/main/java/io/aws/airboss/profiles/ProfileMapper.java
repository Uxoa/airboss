package io.aws.airboss.profiles;

import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    
    public ProfileResponseDTO toResponseDTO(Profile profile) {
        if (profile == null || profile.getProfileId() == null || profile.getProfileId() <= 0) {
            return null;
        }
        
        ProfileResponseDTO responseDTO = new ProfileResponseDTO();
        responseDTO.setId(profile.getProfileId());
        responseDTO.setName(profile.getName());
        responseDTO.setLastName(profile.getLastName());
        responseDTO.setEmail(profile.getEmail());
        responseDTO.setMobile(profile.getMobile());
        responseDTO.setProfileImage(profile.getProfileImage());
        responseDTO.setRegistrationDate(profile.getRegistrationDate());
        responseDTO.setLastLogin(profile.getLastLogin());
        return responseDTO;
    }
    
    public Profile toEntity(ProfileRequestDTO profileRequestDTO) {
        if (profileRequestDTO == null) {
            return null;
        }
        
        Profile profile = new Profile();
        profile.setProfileId(profileRequestDTO.getId());
        profile.setName(profileRequestDTO.getName());
        profile.setLastName(profileRequestDTO.getLastName());
        profile.setEmail(profileRequestDTO.getEmail());
        profile.setMobile(profileRequestDTO.getMobile());
        profile.setProfileImage(profileRequestDTO.getProfileImage());
        profile.setRegistrationDate(profileRequestDTO.getRegistrationDate());
        profile.setLastLogin(profileRequestDTO.getLastLogin());
        return profile;
    }
    
    public Profile toEntity(ProfileResponseDTO profileResponseDTO) {
        if (profileResponseDTO == null) {
            return null;
        }
        
        Profile profile = new Profile();
        profile.setProfileId(profileResponseDTO.getId());
        profile.setName(profileResponseDTO.getName());
        profile.setLastName(profileResponseDTO.getLastName());
        profile.setEmail(profileResponseDTO.getEmail());
        profile.setMobile(profileResponseDTO.getMobile());
        profile.setProfileImage(profileResponseDTO.getProfileImage());
        profile.setRegistrationDate(profileResponseDTO.getRegistrationDate());
        profile.setLastLogin(profileResponseDTO.getLastLogin());
        return profile;
    }
}