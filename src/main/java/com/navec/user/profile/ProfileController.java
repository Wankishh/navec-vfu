package com.navec.user.profile;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/users/{user_id}/profile")
@Tag(name = "Profile")
@SecurityRequirement(name = "apiAuth")
public class ProfileController {
    private final ProfileService profileService;
    public ProfileController(ProfileService profileService) {

        this.profileService = profileService;
    }

    @GetMapping(path = "/")
    public ResponseEntity<ProfileResponseDto> getProfile(
            @PathVariable("user_id") Long ignoredUserId
    ) {
        return ResponseEntity.ok(this.profileService.getProfile());
    }

    @PutMapping(path = "/{profile_id}")
    public ResponseEntity<ProfileResponseDto> update(
            @Valid @RequestBody UpdateProfileRequestDto updateProfileRequestDto,
            @PathVariable("profile_id") Long profileId,
            @PathVariable("user_id") String ignoredUserId) {
        return ResponseEntity.ok(this.profileService.updateProfile(profileId, updateProfileRequestDto));
    }
}
