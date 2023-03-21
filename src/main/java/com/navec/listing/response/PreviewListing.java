package com.navec.listing.response;

import com.navec.image.ImageDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PreviewListing {
    private Long id;
    private String title;

    private Double price;
    private Double priceEu;
    private Integer currency;

    private String publishedFrom;
    private String placeTitle;

    private ImageDto image;
    private List<String> footer;
}
