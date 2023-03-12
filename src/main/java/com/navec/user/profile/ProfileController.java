package com.navec.user.profile;

import com.navec.exception.ResponseException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/users/{user_id}/profile")
public class ProfileController {
    private final ProfileService profileService;
    public ProfileController(ProfileService profileService) {

        this.profileService = profileService;
    }

    @GetMapping(path = "/")
    public ResponseEntity<ProfileResponseDto> getProfile(
            @PathVariable("user_id") Long ignoredUserId
    ) {
        try {
            return ResponseEntity.ok(this.profileService.getProfile());
        } catch (ResponseException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .build();
        }
    }

    @PutMapping(path = "/{profile_id}")
    public ResponseEntity<ProfileResponseDto> update(
            @Valid @RequestBody UpdateProfileRequestDto updateProfileRequestDto,
            @PathVariable("profile_id") Long profileId,
            @PathVariable("user_id") String ignoredUserId) {
        try {
            return ResponseEntity.ok(this.profileService.updateProfile(profileId, updateProfileRequestDto));
        } catch (ResponseException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .build();
        }
    }
}
