package com.navec.listing.response;


import com.navec.address.area.AreaDto;
import com.navec.address.place.PlaceDto;
import com.navec.listing.Listing;
import com.navec.listing.section.SectionDto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ListingResponse {
    private Long id;
    private String title;
    private String description;
    private SectionDto section;
    private AreaDto area;
    private PlaceDto place;
    private Double price;
    private Double priceBg;
    private Double priceEu;
    private Integer currency;
    private String youtubeUrl;
    private Boolean archived;
    private String deletedAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;


    public ListingResponse(Listing listing) {
        this.id = listing.getId();
        this.title = listing.getTitle();
        this.description = listing.getDescription();
        this.area = new AreaDto(listing.getArea());
        this.place = new PlaceDto(listing.getPlace());
        this.section = new SectionDto(listing.getSection());
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
