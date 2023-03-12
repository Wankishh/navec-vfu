package com.navec.listing.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreateListingRequest {
    private String title;

    @Positive
    @NotNull
    private Long sectionId;

    @NotBlank
    private String description;

    @Positive
    @NotNull
    private Integer areaId;
    @Positive
    @NotNull
    private Integer placeId;

    @Positive
    @NotNull
    private Double price;
    @PositiveOrZero
    @NotNull
    private Integer currency;

    private String youtubeUrl;
}
