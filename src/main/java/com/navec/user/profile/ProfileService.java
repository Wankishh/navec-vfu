package com.navec.user.profile;

import com.navec.address.area.Area;
import com.navec.address.area.AreaService;
import com.navec.address.place.Place;
import com.navec.address.place.PlaceService;
import com.navec.exception.ResponseException;
import com.navec.user.Role;
import com.navec.user.User;
import com.navec.user.UserRepository;
import com.navec.user.UserService;
import com.navec.utils.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class ProfileService {
    private final UserRepository userRepository;

    private final UserService userService;

    private final AreaService areaService;

    private final ProfileRepository profileRepository;
    private final PlaceService placeService;

    public ProfileService(UserService userService, AreaService areaService, ProfileRepository profileRepository, PlaceService placeService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.areaService = areaService;
        this.profileRepository = profileRepository;
        this.placeService = placeService;
        this.userRepository = userRepository;
    }

    public ProfileResponseDto updateProfile(
            Long id,
            UpdateProfileRequestDto updateProfileRequestDto
    ) throws ResponseException {
        User user = this.userService.getCurrentUser();
        Profile profile = getProfileOrFail(id);

        boolean isAdmin = user.getRole() == Role.ADMIN;
        boolean isSameUser = Objects.equals(user.getId(), profile.getUser().getId());
        if (!isSameUser && !isAdmin) {
            log.error("Profile is not found or is different than current user");
            throw new ResponseException(HttpStatus.FORBIDDEN);
        }

        Area area = this.areaService.findById(updateProfileRequestDto.getAreaId());
        Place place = this.placeService.findById(updateProfileRequestDto.getPlaceId());

        profile.setUpdatedAt(TimestampUtils.getCurrentTimestamp());
        profile.setName(updateProfileRequestDto.getName());
        profile.setPhone(updateProfileRequestDto.getPhone());
        if (updateProfileRequestDto.getLongtitude() != null) {
            profile.setLongtitude(updateProfileRequestDto.getLongtitude());
            profile.setLatitude(updateProfileRequestDto.getLatitude());
        }

        profile.setArea(area);
        profile.setPlace(place);

        Profile newProfile = this.profileRepository.save(profile);

        user.setName(updateProfileRequestDto.getName());
        userRepository.save(user);

        return new ProfileResponseDto(newProfile);
    }

    public ProfileResponseDto getProfile() {
        User currentUser = this.userService.getCurrentUser();
        Profile currentProfile = this.getProfileByUserOrFail(currentUser);
        return new ProfileResponseDto(currentProfile);
    }

    private Profile getProfileOrFail(Long id) {
        return this.profileRepository.findById(id)
                .orElseThrow(ProfileNotFoundException::new);
    }

    private Profile getProfileByUserOrFail(User user) {
        return this.profileRepository.findByUser(user)
                .orElseThrow(ProfileNotFoundException::new);
    }


    public static class ProfileNotFoundException extends RuntimeException {
        public ProfileNotFoundException() {
            super("Profile not found");
        }
    }
}
