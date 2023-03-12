package com.navec.user.profile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProfileRequestDto {
    @NotEmpty
    private String name;

    @NotEmpty
    private String phone;

    @NotNull
    private Integer areaId;

    @NotNull
    private Integer placeId;

    private Double longtitude;
    private Double latitude;
}
