package com.navec.section;

import com.navec.listing.Listing;
import com.navec.listing.brand.Brand;
import com.navec.filter.Filter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "sections")
public class Section {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Column(name = "seo_title")
    private String seoTitle;
    private Integer images;
    private String slug;
    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
    private List<Brand> brands;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Listing> listings;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private java.sql.Timestamp updatedAt;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "section_id")
    private List<Filter> filters = new ArrayList<>();

}
