package com.navec.listing;

import com.navec.address.area.Area;
import com.navec.address.place.Place;
import com.navec.listing.section.Section;
import com.navec.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

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
