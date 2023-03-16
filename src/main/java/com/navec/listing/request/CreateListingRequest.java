package com.navec.listing.request;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

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

    @NotEmpty
    @Size(min = 1, max = 20)
    private List<Long> images;
}
