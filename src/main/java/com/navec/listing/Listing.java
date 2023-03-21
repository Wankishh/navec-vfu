package com.navec.listing;

import com.navec.address.area.Area;
import com.navec.address.place.Place;
import com.navec.comment.Comment;
import com.navec.image.Image;
import com.navec.brand.Brand;
import com.navec.brand_model.BrandModel;
import com.navec.listing_filter.ListingFilter;
import com.navec.section.Section;
import com.navec.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "listings")
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "searched-listings",
                attributeNodes = {
                        @NamedAttributeNode(value = "images"),
                        @NamedAttributeNode(value = "place"),
                }
        )
)
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "listing")
    private List<Image> images;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "listing")
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_model_id")
    private BrandModel brandModel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @OneToMany(mappedBy = "listing", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ListingFilter> listingFilters;

    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "FLOAT(11,2)", nullable = false)
    private Double price;

    @Column(name = "price_bg", columnDefinition = "FLOAT(11,2)", nullable = false)
    private Double priceBg;
    @Column(name = "price_eu", columnDefinition = "FLOAT(11,2)", nullable = false)
    private Double priceEu;

    @Column(columnDefinition = "TINYINT DEFAULT 0")
    private Integer currency;

    @Column(name = "youtube_url")
    private String youtubeUrl;

    private Boolean archived;

    @Column(columnDefinition = "INT unsigned DEFAULT 0")
    private Integer watchers;

    @Column(name = "deleted_at", columnDefinition = "DATETIME DEFAULT null")
    private java.sql.Timestamp deletedAt;
    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = false)
    private java.sql.Timestamp updatedAt;
}
