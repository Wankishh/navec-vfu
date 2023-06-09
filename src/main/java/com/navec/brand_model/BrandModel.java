package com.navec.brand_model;

import com.navec.brand.Brand;
import com.navec.listing.Listing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "brand_models")
public class BrandModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serie_id")
    private BrandModelSerie serie;

    @OneToMany(mappedBy = "brandModel")
    private Collection<Listing> listings;

    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    @Column(columnDefinition = "INT DEFAULT 1")
    private Integer order;

    @Column(name = "seo_title", nullable = false)
    private String seoTitle;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private java.sql.Timestamp updatedAt;
}
