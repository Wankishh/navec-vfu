package com.navec.brand;

import com.navec.brand_model.BrandModelDto;
import lombok.Data;

import java.util.List;

@Data
public class BrandDto {
    private Long id;
    private String name;
    private String slug;

    private Integer order;
    private String imageUrl;
    private String seoTitle;

    private List<BrandModelDto> brandModels;

    public BrandDto(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.slug = brand.getSlug();
        this.order = brand.getOrder();
        this.imageUrl = brand.getImageUrl();
        this.seoTitle = brand.getSeoTitle();
        this.brandModels = brand.getModels()
                .stream()
                .map(BrandModelDto::new)
                .toList();
    }
}
