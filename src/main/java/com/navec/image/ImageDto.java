package com.navec.image;


import lombok.Data;

@Data
public class ImageDto {
    private Long id;
    private String name;
    private String originalName;
    private String uri;
    public ImageDto(Image image, String baseImageUri) {
        this.id = image.getId();
        this.name = image.getName();
        this.originalName = image.getOriginalName();
        this.uri = baseImageUri + image.getName();
    }
}
