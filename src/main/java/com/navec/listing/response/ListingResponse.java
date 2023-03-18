package com.navec.listing.response;


import com.navec.address.area.AreaDto;
import com.navec.address.place.PlaceDto;
import com.navec.comment.CommentDto;
import com.navec.image.ImageDto;
import com.navec.listing.Listing;
import com.navec.section.SectionDto;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class ListingResponse {
    private Long id;
    private String title;
    private String description;
    private SectionDto section;
    private AreaDto area;
    private PlaceDto place;

    private List<ImageDto> images;
    private List<CommentDto> comments;
    private Double price;
    private Double priceBg;
    private Double priceEu;
    private Integer currency;
    private String youtubeUrl;
    private Boolean archived;
    private String deletedAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public ListingResponse(Listing listing, String baseImageUri) {
        this.id = listing.getId();
        this.title = listing.getTitle();
        this.description = listing.getDescription();
        this.area = new AreaDto(listing.getArea());
        this.place = new PlaceDto(listing.getPlace());
        this.section = new SectionDto(listing.getSection());
        this.images = listing.getImages() != null ?
                listing.getImages()
                        .stream()
                        .map(image -> new ImageDto(image, baseImageUri))
                        .toList() : new ArrayList<>();
        this.comments = listing.getComments() != null ?
                listing.getComments()
                        .stream()
                        .map(CommentDto::new)
                        .toList() : new ArrayList<>();
        this.price = listing.getPrice();
        this.priceBg = listing.getPriceBg();
        this.priceEu = listing.getPriceEu();
        this.currency = listing.getCurrency();
        this.youtubeUrl = listing.getYoutubeUrl();
        this.archived = listing.getArchived();
        this.deletedAt = listing.getDeletedAt() != null ? listing.getDeletedAt().toString() : null;
        this.createdAt = listing.getCreatedAt();
        this.updatedAt = listing.getUpdatedAt();
    }
}
