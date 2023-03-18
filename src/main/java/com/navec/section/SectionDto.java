package com.navec.section;


import lombok.Data;

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

    public SectionDto(Section section) {
        this.id = section.getId();
        this.name = section.getName();
        this.seoTitle = section.getSeoTitle();
        this.images = section.getImages();
        this.slug = section.getSlug();
        this.imageUrl = section.getImageUrl();
        this.createdAt = section.getCreatedAt().toString();
        this.updatedAt = section.getUpdatedAt().toString();
    }
}
