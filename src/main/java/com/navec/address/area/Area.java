package com.navec.address.area;


import com.navec.address.place.Place;
import com.navec.listing.Listing;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "areas")
public class Area {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String slug;

    private Double longtitude;

    private Double latitude;

    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Place> places;


    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    private List<Listing> listings;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private java.sql.Timestamp updatedAt;
}
