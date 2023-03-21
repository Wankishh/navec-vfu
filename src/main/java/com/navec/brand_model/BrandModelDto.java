package com.navec.brand_model;

import lombok.Data;

@Data
public class BrandModelDto {
    private Long id;
    private String name;
    private String slug;
    private BrandModelSerieDto serie;
    private Integer order;
    private String seoTitle;

    private String createdAt;
    private String updatedAt;

    public BrandModelDto(BrandModel brandModel) {
        this.id = brandModel.getId();
        this.name = brandModel.getName();
        this.slug = brandModel.getSlug();
        if (brandModel.getSerie() != null) {
            this.serie = new BrandModelSerieDto(brandModel.getSerie());
        }
        this.order = brandModel.getOrder();
        this.seoTitle = brandModel.getSeoTitle();
        this.createdAt = brandModel.getCreatedAt().toString();
        this.updatedAt = brandModel.getUpdatedAt().toString();
    }


}
