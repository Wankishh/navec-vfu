package com.navec.address.area;

import lombok.Data;

@Data
public class AreaDto {
    private Integer id;
    private String name;
    private String slug;
    private Double longtitude;
    private Double latitude;

    public AreaDto(Area area) {
        super();
        this.id = area.getId();
        this.latitude = area.getLatitude();
        this.longtitude = area.getLongtitude();
        this.name = area.getName();
        this.slug = area.getSlug();
    }
}
