package com.navec.section;


import com.navec.brand.BrandDto;
import com.navec.extras.CategoryExtraDto;
import com.navec.filter.FilterDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SectionDto {
    private final Long id;
    private final String name;
    private final String seoTitle;
    private final Integer images;
    private final String slug;
    private final String imageUrl;
    private final String createdAt;
    private final String updatedAt;

    /**
     * Those are set outside in order to bypass multiple bag exception
     */
    private List<BrandDto> brands;
    private List<FilterDto> filters;

    private List<CategoryExtraDto> categoryExtras;

    public SectionDto(Section section) {
        this.id = section.getId();
        this.name = section.getName();
        this.seoTitle = section.getSeoTitle();
        this.images = section.getImages();
        this.slug = section.getSlug();
        this.imageUrl = section.getImageUrl();
        this.createdAt = section.getCreatedAt().toString();
        this.updatedAt = section.getUpdatedAt().toString();
        this.brands = new ArrayList<>();
        this.filters = new ArrayList<>();
        this.categoryExtras = new ArrayList<>();
    }
}
