package com.navec.address.place;

import com.navec.address.area.AreaDto;
import lombok.Data;

@Data
public class PlaceDto {
    private Integer id;
    private AreaDto area;
    private String name;
    private String slug;
    private Double longtitude;
    private Double latitude;

    public PlaceDto(Place place) {
        super();
        this.id = place.getId();
        this.area = new AreaDto(place.getArea());
        this.name = place.getName();
        this.slug = place.getSlug();
        this.longtitude = place.getLongtitude();
        this.latitude = place.getLatitude();
    }
}

