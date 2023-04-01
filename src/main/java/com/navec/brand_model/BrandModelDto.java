package com.navec.brand_model;

import lombok.Data;

@Data
public class BrandModelDto {
    private Long id;
    private String name;
    private String slug;
    private Integer order;
    private String seoTitle;

    private String createdAt;
    private String updatedAt;

    public BrandModelDto(BrandModel brandModel) {
        this.id = brandModel.getId();
        this.name = brandModel.getName();
        this.slug = brandModel.getSlug();
        this.order = brandModel.getOrder();
        this.seoTitle = brandModel.getSeoTitle();
        this.createdAt = brandModel.getCreatedAt().toString();
        this.updatedAt = brandModel.getUpdatedAt().toString();
    }


}
