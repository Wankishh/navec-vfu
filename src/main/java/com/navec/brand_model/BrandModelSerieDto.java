package com.navec.brand_model;

import lombok.Data;

@Data
public class BrandModelSerieDto {
    private long id;
    private String name;
    private String type;

    private String createdAt;
    private String updatedAt;

    public BrandModelSerieDto(BrandModelSerie serie) {
        this.id = serie.getId();
        this.name = serie.getName();
        this.type = serie.getType();
        this.createdAt = serie.getCreatedAt().toString();
        this.updatedAt = serie.getUpdatedAt().toString();
    }
}
