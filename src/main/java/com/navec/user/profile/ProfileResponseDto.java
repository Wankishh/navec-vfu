package com.navec.user.profile;

import com.navec.address.area.AreaDto;
import com.navec.address.place.PlaceDto;
import lombok.Data;

@Data
public class ProfileResponseDto {

    private Long id;
    private String name;

    private String email;
    private String phone;
    private AreaDto area;
    private PlaceDto place;
    private Double longtitude;
    private Double latitude;

    private String createdAt;
    private String updatedAt;

    public ProfileResponseDto(Profile profile) {
        this.id = profile.getId();
        this.name = profile.getName();
        this.email = profile.getUser().getEmail();
        this.phone = profile.getPhone();
        this.area = profile.getArea() != null ? new AreaDto(profile.getArea()) : null;
        this.place = profile.getPlace() != null ? new PlaceDto(profile.getPlace()) : null;
        this.longtitude = profile.getLongtitude();
        this.latitude = profile.getLatitude();
        this.createdAt = profile.getCreatedAt().toString();
        this.updatedAt = profile.getUpdatedAt().toString();
    }
}
